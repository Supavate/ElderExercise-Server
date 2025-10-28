package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.DTO.ExerciseSessionDetailListView;
import com.example.elderexserver.data.patient.Patient;
import com.example.elderexserver.data.routine.DTO.*;
import com.example.elderexserver.data.routine.Patient_Routine;
import com.example.elderexserver.data.routine.Routine;
import com.example.elderexserver.repository.PatientRepository;
import com.example.elderexserver.repository.PatientRoutineRepository;
import com.example.elderexserver.repository.RoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PatientRoutineService {

    @Autowired
    private PatientRoutineRepository patientRoutineRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RoutineRepository routineRepository;

    public List<ExerciseSessionDetailListView> getActualExerciseDetailListByPatientIdAndDate(String date, Integer patientId) {
        return patientRoutineRepository.findActualExerciseDetailListByPatientIdAndDate(date, patientId);
    }

    public List<PatientRoutineView> getPatientRoutineByPatientId(Integer patientId) {
        return patientRoutineRepository.findPatientRoutineByPatientId(patientId);
    }

    public PatientRoutineList getCurrentPatientRoutineByPatientId(Integer patientId) {
        List<PatientRoutineView> patientRoutineViews = patientRoutineRepository.findCurrentPatientRoutineByPatientId(patientId);

        PatientRoutineView firstRow = patientRoutineViews.get(0);
        PatientRoutineList routine = new PatientRoutineList(
                firstRow.getRoutineName(),
                firstRow.getRoutineDescription(),
                firstRow.getPatientRoutineId(),
                new HashSet<>()
        );

        for (PatientRoutineView row : patientRoutineViews) {

            PatientRoutineList.Exercise exercise = new PatientRoutineList.Exercise(
                    row.getExerciseId(),
                    row.getExerciseName(),
                    row.getRep(),
                    row.getSet(),
                    row.getDay()
            );

            routine.getExercises().add(exercise);
        }

        return routine;
    }

    public Patient_Routine newPatientRoutine(NewPatientRoutine patientRoutine) {
        Patient patient = patientRepository.findById(patientRoutine.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        Routine routine = routineRepository.findById(patientRoutine.getRoutineId())
                .orElseThrow(() -> new IllegalArgumentException("Routine not found"));

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

        List<PatientCurrentWeekProgressRoutine.Exercise> exercises = new ArrayList<>();

        for (PatientCurrentWeekProgressRoutineView row : view) {
            PatientCurrentWeekProgressRoutine.Exercise exercise = new PatientCurrentWeekProgressRoutine.Exercise(
                    row.getExerciseId(),
                    row.getExerciseName(),
                    row.getGoalHit(),
                    row.getGoal()
            );

            exercises.add(exercise);
        }

        PatientCurrentWeekProgressRoutineView firstRow = view.get(0);
        PatientCurrentWeekProgressRoutine result = new PatientCurrentWeekProgressRoutine(
                firstRow.getRoutineId(),
                firstRow.getRoutineName(),
                exercises
        );

        return result;
    }

    public List<PatientCurrentDayProgressRoutineView> getPatientCurrentDayRoutineByPatientId(Integer patientId) {
        List<PatientCurrentDayProgressRoutineView> result = patientRoutineRepository.findCurrentDayPatientRoutineStatusByPatientId(patientId);
        return (result.isEmpty()) ? null : result;
    }

    public List<PatientProgressDashboardView> getPatientCurrentWeekRoutineByPatientId(Integer patientId) {
        List<PatientProgressDashboardView> result = patientRoutineRepository.findPatientProgressDashboardByPatientId(patientId);
        return (result.isEmpty()) ? null : result;
    }
}
