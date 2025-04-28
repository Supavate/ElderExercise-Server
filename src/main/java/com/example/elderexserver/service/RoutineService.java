package com.example.elderexserver.service;

import com.example.elderexserver.data.routine.DTO.RoutineList;
import com.example.elderexserver.data.routine.DTO.RoutineListView;
import com.example.elderexserver.repository.RoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoutineService {

    @Autowired
    private RoutineRepository routineRepository;

    public List<RoutineList> findRoutineList() {
        List<RoutineListView> routineList = routineRepository.findRoutineList();

        Map<Integer, RoutineList> routineMap = new LinkedHashMap<>();

        for (RoutineListView row : routineList) {
            RoutineList routine = routineMap.computeIfAbsent(row.getRoutineId(),
                    id -> new RoutineList(id, row.getRoutineName(), row.getRoutineDescription(), row.getStaffFirstName(), row.getStaffLastName(), new HashSet<>()));

            RoutineList.Exercise exercise = routine.getExercise().stream()
                    .filter(x -> x.getExerciseId().equals(row.getExerciseId())).findFirst().orElseGet(() -> {
                        RoutineList.Exercise newExercise = new RoutineList.Exercise(row.getExerciseId(), row.getExerciseName(), new HashSet<>());
                        routine.getExercise().add(newExercise);
                        return newExercise;
                    });

            if (row.getDayId() != null && exercise.getDay().stream().noneMatch(x -> x.getDayId().equals(row.getDayId()))) {
                exercise.getDay().add(new RoutineList.Day(row.getDayId(), row.getRep()));
            }
        }

        return new ArrayList<>(routineMap.values());
    }
}
