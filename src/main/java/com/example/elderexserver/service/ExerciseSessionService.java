package com.example.elderexserver.service;

import com.example.elderexserver.Exception.ResourceNotFoundException;
import com.example.elderexserver.data.exercise.DTO.NewExerciseSession;
import com.example.elderexserver.data.webSocket.OngoingSession;
import com.example.elderexserver.data.exercise.Exercise;
import com.example.elderexserver.data.exercise.Exercise_Session;
import com.example.elderexserver.data.exercise.Exercise_Session_Detail;
import com.example.elderexserver.data.routine.DTO.ExerciseSessionHistory;
import com.example.elderexserver.data.routine.DTO.ExerciseSessionHistoryView;
import com.example.elderexserver.data.routine.Patient_Routine;
import com.example.elderexserver.repository.ExerciseRepository;
import com.example.elderexserver.repository.ExerciseSessionRepository;
import com.example.elderexserver.repository.PatientRoutineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExerciseSessionService {

    private final ExerciseSessionRepository exerciseSessionRepository;
    private final PatientRoutineRepository patientRoutineRepository;
    private final ExerciseRepository exerciseRepository;

    public List<ExerciseSessionHistory> findAllHistoryByPatientId(int patientId) {
        List<ExerciseSessionHistoryView> views = exerciseSessionRepository.findAllByPatientId(patientId);

        if (views.isEmpty()) {
            return Collections.emptyList();
        }

        return createPatientHistoryList(views);
    }

    public List<ExerciseSessionHistory> findHistoryByPatientIdWithOffset(int patientId, int offsetAmount) {
        final int limit = 5;
        List<ExerciseSessionHistoryView> views = exerciseSessionRepository.findAllByPatientId(patientId, limit, offsetAmount * limit);

        if (views.isEmpty()) {
            return Collections.emptyList();
        }

        return createPatientHistoryList(views);
    }

    private List<ExerciseSessionHistory> createPatientHistoryList(List<ExerciseSessionHistoryView> views) {
        LinkedHashMap<String, ExerciseSessionHistory> result = new LinkedHashMap<>();
        Map<Integer, ExerciseSessionHistory.Session> sessionMap = new HashMap<>();

        for (ExerciseSessionHistoryView row : views) {
            ExerciseSessionHistory date = result.computeIfAbsent(row.getDate(), k -> new ExerciseSessionHistory(
                    row.getDate(),
                    row.getPatientRoutineId(),
                    new ArrayList<>()
            ));

            ExerciseSessionHistory.Session session = sessionMap.computeIfAbsent(row.getId(), k -> {
                ExerciseSessionHistory.Session newSession = new ExerciseSessionHistory.Session(
                        row.getId(),
                        row.getSessionTime(),
                        new ArrayList<>()
                );
                date.getSessions().add(newSession);
                return newSession;
            });

            session.getExercises().add(
                    new ExerciseSessionHistory.Exercise(
                            row.getExerciseId(),
                            row.getName(),
                            row.getReps()
                    ));
        }

        return new ArrayList<>(result.values());
    }

    @Transactional
    public Exercise_Session newExerciseSession(NewExerciseSession newExerciseSession) {
        Patient_Routine patientRoutine = patientRoutineRepository.findById(newExerciseSession.getPatientRoutineId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient Routine not found"));

        Exercise_Session session = new Exercise_Session();
        session.setPatientRoutine(patientRoutine);
        session.setStart_time(newExerciseSession.getStartTime());
        session.setEnd_time(newExerciseSession.getEndTime());

        List<Exercise_Session_Detail> details = newExerciseSession.getDetails().stream().map(detail -> {
            Exercise exercise = exerciseRepository.findById(detail.getExerciseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Exercise not found for ID " + detail.getExerciseId()));

            Exercise_Session_Detail sessionDetail = new Exercise_Session_Detail();
            sessionDetail.setExerciseSession(session);
            sessionDetail.setExercise(exercise);
            sessionDetail.setStart_time(detail.getStartTime());
            sessionDetail.setEnd_time(detail.getEndTime());
            sessionDetail.setReps(detail.getReps());

            return sessionDetail;
        }).toList();

        session.setExercise_session_details(details);
        return exerciseSessionRepository.save(session);
    }

    @Transactional
    public void deleteExerciseSession(Integer sessionId) {
        Exercise_Session session = exerciseSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise session not found with id: " + sessionId));

        exerciseSessionRepository.delete(session);
    }

    @Transactional
    public void save(Exercise_Session session) {
        exerciseSessionRepository.save(session);
    }

    @Transactional
    public void saveSessionToDatabase(String sessionId, OngoingSession ongoingSession, LocalDateTime endTime) {
        if (ongoingSession == null || ongoingSession.getSession() == null) {
            log.error("Cannot save session {} — missing session data.", sessionId);
            return;
        }
        try {
            Exercise_Session session = ongoingSession.getSession();
            session.setEnd_time(endTime);
            exerciseSessionRepository.save(session);
            log.info("💾 Saved session to database: SessionID={}", sessionId);
        } catch (Exception e) {
            log.error("Failed to save session {}: {}", sessionId, e.getMessage(), e);
            throw e;
        }
    }
}

