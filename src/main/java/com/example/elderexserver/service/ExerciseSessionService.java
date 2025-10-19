package com.example.elderexserver.service;

import com.example.elderexserver.data.routine.DTO.ExerciseSessionHistory;
import com.example.elderexserver.data.routine.DTO.ExerciseSessionHistoryView;
import com.example.elderexserver.repository.ExerciseSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ExerciseSessionService {
    @Autowired
    private ExerciseSessionRepository exerciseSessionRepository;

    public List<ExerciseSessionHistory> findAllHistoryByPatientId(int patientId) {
        List<ExerciseSessionHistoryView> views = exerciseSessionRepository.findAllByPatientId(patientId);
        HashMap<Integer, ExerciseSessionHistory> result = new HashMap<>();

        for (ExerciseSessionHistoryView row : views) {
            result.computeIfAbsent(row.getId(), k -> new ExerciseSessionHistory(
                    row.getId(),
                    row.getPatientRoutineId(),
                    row.getSessionTime(),
                    row.getDate(),
                    new ArrayList<>()
            ));

            result.get(row.getId()).getExercises().add(
                    new ExerciseSessionHistory.Exercise(
                            row.getExerciseId(),
                            row.getName(),
                            row.getReps()
                    ));
        }

        return new ArrayList<>(result.values());
    }
}
