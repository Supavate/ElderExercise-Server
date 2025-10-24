package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.DTO.NewExerciseSession;
import com.example.elderexserver.data.exercise.Exercise;
import com.example.elderexserver.data.exercise.Exercise_Session;
import com.example.elderexserver.data.exercise.Exercise_Session_Detail;
import com.example.elderexserver.data.routine.DTO.ExerciseSessionHistory;
import com.example.elderexserver.data.routine.DTO.ExerciseSessionHistoryView;
import com.example.elderexserver.data.routine.Patient_Routine;
import com.example.elderexserver.repository.ExerciseRepository;
import com.example.elderexserver.repository.ExerciseSessionDetailRepository;
import com.example.elderexserver.repository.ExerciseSessionRepository;
import com.example.elderexserver.repository.PatientRoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ExerciseSessionService {
    @Autowired
    private ExerciseSessionRepository exerciseSessionRepository;

    @Autowired
    private PatientRoutineRepository patientRoutineRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseSessionDetailRepository exerciseSessionDetailRepository;

    public List<ExerciseSessionHistory> findAllHistoryByPatientId(int patientId) {
        List<ExerciseSessionHistoryView> views = exerciseSessionRepository.findAllByPatientId(patientId);
        LinkedHashMap<String, ExerciseSessionHistory> result = new LinkedHashMap<>();
        HashMap<Integer, ExerciseSessionHistory.Session> sessionMap = new HashMap<>();

        for (ExerciseSessionHistoryView row : views) {
            ExerciseSessionHistory  date = result.computeIfAbsent(row.getDate(), k -> new ExerciseSessionHistory(
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

        Patient_Routine patient_routine = patientRoutineRepository.findById(newExerciseSession.getPatientRoutineId())
                .orElseThrow(() -> new RuntimeException("Patient Routine not found"));

        Exercise_Session session = new Exercise_Session();
        session.setPatientRoutine(patient_routine);
        session.setStart_time(newExerciseSession.getStartTime());
        session.setEnd_time(newExerciseSession.getEndTime());

        List<Exercise_Session_Detail> details = new ArrayList<>();

        for (NewExerciseSession.Detail detail : newExerciseSession.getDetails()) {

            Exercise exercise = exerciseRepository.findById(detail.getExerciseId())
                    .orElseThrow(() -> new RuntimeException("Exercise in Session Detail not found"));

            Exercise_Session_Detail sessionDetail = new Exercise_Session_Detail();

            sessionDetail.setExerciseSession(session);
            sessionDetail.setExercise(exercise);
            sessionDetail.setStart_time(detail.getStartTime());
            sessionDetail.setEnd_time(detail.getEndTime());
            sessionDetail.setReps(detail.getReps());

            details.add(sessionDetail);
        }

        session.setExercise_session_details(details);

        return exerciseSessionRepository.save(session);
    }

    @Transactional
    public void deleteExerciseSession(Integer sessionId) {
        Exercise_Session session = exerciseSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Exercise session not found"));

        exerciseSessionRepository.delete(session);
    }
}
