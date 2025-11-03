package com.example.elderexserver.service;

import com.example.elderexserver.Exception.ResourceNotFoundException;
import com.example.elderexserver.data.exercise.DTO.ExerciseSessionDetailListView;
import com.example.elderexserver.data.patient.Patient;
import com.example.elderexserver.data.routine.DTO.*;
import com.example.elderexserver.data.routine.Patient_Routine;
import com.example.elderexserver.data.routine.Routine;
import com.example.elderexserver.repository.PatientRepository;
import com.example.elderexserver.repository.PatientRoutineRepository;
import com.example.elderexserver.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PatientRoutineService {

    private final PatientRoutineRepository patientRoutineRepository;
    private final PatientRepository patientRepository;
    private final RoutineRepository routineRepository;

    public List<ExerciseSessionDetailListView> getActualExerciseDetailListByPatientIdAndDate(String date, Integer patientId) {
        return patientRoutineRepository.findActualExerciseDetailListByPatientIdAndDate(date, patientId);
    }

    public List<PatientRoutineView> getPatientRoutineByPatientId(Integer patientId) {
        return patientRoutineRepository.findPatientRoutineByPatientId(patientId);
    }

    public PatientRoutineList getCurrentPatientRoutineViewByPatientId(Integer patientId) {
        List<PatientRoutineView> patientRoutineViews = patientRoutineRepository.findCurrentPatientRoutineViewByPatientId(patientId);
        if (patientRoutineViews.isEmpty()) return null;

        PatientRoutineView firstRow = patientRoutineViews.get(0);
        PatientRoutineList routine = new PatientRoutineList(
                firstRow.getRoutineName(),
                firstRow.getRoutineDescription(),
                firstRow.getPatientRoutineId(),
                new HashSet<>()
        );

        for (PatientRoutineView row : patientRoutineViews) {
            routine.getExercises().add(new PatientRoutineList.Exercise(
                    row.getExerciseId(),
                    row.getExerciseName(),
                    row.getRep(),
                    row.getSet(),
                    row.getDay()
            ));
        }

        return routine;
    }

    @Transactional
    public Patient_Routine newPatientRoutine(NewPatientRoutine patientRoutine) {
        Patient patient = patientRepository.findById(patientRoutine.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Routine routine = routineRepository.findById(patientRoutine.getRoutineId())
                .orElseThrow(() -> new ResourceNotFoundException("Routine not found"));

        Patient_Routine patient_routine = new Patient_Routine(
                patient,
                routine,
                LocalDate.parse(patientRoutine.getStartDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalDate.parse(patientRoutine.getEndDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );

        return patientRoutineRepository.save(patient_routine);
    }

    public PatientCurrentWeekProgressRoutine getPatientCurrentWeekProgressRoutine(Integer patientId) {
        List<PatientCurrentWeekProgressRoutineView> view = patientRoutineRepository.findCurrentWeekPatientRoutineStatusByPatientId(patientId);
        if (view.isEmpty()) return null;

        List<PatientCurrentWeekProgressRoutine.Exercise> exercises = view.stream()
                .map(row -> new PatientCurrentWeekProgressRoutine.Exercise(
                        row.getExerciseId(),
                        row.getExerciseName(),
                        row.getGoalHit(),
                        row.getGoal()))
                .toList();

        PatientCurrentWeekProgressRoutineView firstRow = view.get(0);
        return new PatientCurrentWeekProgressRoutine(firstRow.getRoutineId(), firstRow.getRoutineName(), exercises);
    }

    public List<PatientCurrentDayProgressRoutineView> getPatientCurrentDayRoutineByPatientId(Integer patientId) {
        return patientRoutineRepository.findCurrentDayPatientRoutineStatusByPatientId(patientId);
    }

    public List<PatientProgressDashboardView> getPatientCurrentWeekRoutineByPatientId(Integer patientId) {
        return patientRoutineRepository.findPatientProgressDashboardByPatientId(patientId);
    }

    public Patient_Routine getCurrentPatientRoutineByPatientId(Integer patientId) {
        return patientRoutineRepository.findCurrentPatientRoutineByPatientId(patientId);
    }
}

