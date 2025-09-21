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

    public PatientRoutineView getCurrentPatientRoutineByPatientId(Integer patientId) {
        return patientRoutineRepository.findCurrentPatientRoutineByPatientId(patientId);
    }
}
