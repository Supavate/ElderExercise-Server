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

    public List<RoutineList> findRoutineList() {
        List<RoutineListView> routineList = exerciseRepository.findRoutineList();

        Map<Integer, RoutineList> routineMap = new LinkedHashMap<>();

        for (RoutineListView row : routineList) {
            RoutineList routine = routineMap.computeIfAbsent(row.getRoutineId(),
                    id -> new RoutineList(id, row.getRoutineName(), row.getRoutineDescription(), row.getStaffFirstName(), row.getStaffLastName(), new HashSet<>()));

            Map<Integer, RoutineList.Exercise> exerciseMap = new HashMap<>();

            RoutineList.Exercise exercise = exerciseMap.computeIfAbsent(row.getExerciseId(),
                    id -> {
                        RoutineList.Exercise newExercise = new RoutineList.Exercise(row.getExerciseId(), row.getExerciseName(), new HashSet<>());
                        routine.getExercise().add(newExercise);
                        return newExercise;
                    });

            exercise.getDay().add(new RoutineList.Day(row.getDayId(), row.getRep()));
        }

        return new ArrayList<>(routineMap.values());
    }
}
