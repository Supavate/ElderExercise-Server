package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.DTO.ExerciseSessionDetailListView;
import com.example.elderexserver.data.routine.DTO.*;
import com.example.elderexserver.repository.PatientRoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientRoutineService {

    @Autowired
    private PatientRoutineRepository patientRoutineRepository;

    public List<ExerciseSessionDetailListView> getActualExerciseDetailListByPatientIdAndDate(String date, Integer patientId) {
        return patientRoutineRepository.findActualExerciseDetailListByPatientIdAndDate(date, patientId);
    }

    public List<PatientRoutineView> getPatientRoutineByPatientId(Integer patientId) {
        return patientRoutineRepository.findPatientRoutineByPatientId(patientId);
    }

    public PatientRoutine getCurrentPatientRoutineByPatientId(Integer patientId) {
        List<PatientRoutineView> patientRoutineViews = patientRoutineRepository.findCurrentPatientRoutineByPatientId(patientId);

        PatientRoutineView firstRow = patientRoutineViews.get(0);
        PatientRoutine routine = new PatientRoutine(
                firstRow.getRoutineName(),
                firstRow.getRoutineDescription(),
                firstRow.getPatientRoutineId(),
                new HashSet<>()
        );

        for (PatientRoutineView row : patientRoutineViews) {

            PatientRoutine.Exercise exercise = new PatientRoutine.Exercise(
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
}
