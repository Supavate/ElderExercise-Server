package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.DTO.RoutineList;
import com.example.elderexserver.data.exercise.DTO.RoutineListView;
import com.example.elderexserver.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;

    public List<RoutineList> getRoutineList() {
        List<RoutineListView> routineList = exerciseRepository.getRoutineList();

        Map<Integer, RoutineList> routineMap = new LinkedHashMap<>();

        for (RoutineListView row : routineList) {
            RoutineList routine = routineMap.computeIfAbsent(row.getRoutineId(),
                    id -> new RoutineList(id, row.getRoutineName(), row.getRoutineDescription(),
                            row.getStaffFirstName(), row.getStaffLastName(), new HashSet<>()));

            RoutineList.Exercise exercise = routine.getExercise().stream()
                    .filter(e -> e.getExerciseId().equals(row.getExerciseId()))
                    .findFirst()
                    .orElseGet(() -> {
                        RoutineList.Exercise newExercise = new RoutineList.Exercise(row.getExerciseId(), row.getExerciseName(), new HashSet<>());
                        routine.getExercise().add(newExercise);
                        return newExercise;
                    });

            exercise.getDay().add(new RoutineList.Day(row.getDayId(), row.getRep()));
        }

        return new ArrayList<>(routineMap.values());
    }
}
