package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.Exercise;
import com.example.elderexserver.data.routine.DTO.NewRoutine;
import com.example.elderexserver.data.routine.DTO.RoutineList;
import com.example.elderexserver.data.routine.DTO.RoutineListView;
import com.example.elderexserver.data.routine.Routine;
import com.example.elderexserver.data.routine.Routine_exercises;
import com.example.elderexserver.data.staff.Staff;
import com.example.elderexserver.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final ExerciseRepository exerciseRepository;
    private final StaffRepository staffRepository;
    private final PatientRoutineService patientRoutineService;

    public RoutineList getRoutineListById(Integer routineId) {
        List<RoutineListView> routineListView = routineRepository.findRoutineListById(routineId);
        if (routineListView.isEmpty()) return null;

        RoutineListView firstRow = routineListView.get(0);
        RoutineList routine = new RoutineList(
                firstRow.getRoutineId(),
                firstRow.getRoutineName(),
                firstRow.getRoutineDescription(),
                firstRow.getStaffFirstName(),
                firstRow.getStaffLastName(),
                new HashSet<>()
        );

        for (RoutineListView row : routineListView) {
            routine.exercise.add(new RoutineList.Exercise(
                    row.getExerciseId(),
                    row.getExerciseName(),
                    row.getRep(),
                    row.getSet(),
                    row.getDay()
            ));
        }

        return routine;
    }

    public List<RoutineList> getRoutineList() {
        List<RoutineListView> routineList = routineRepository.findRoutineList();
        Map<Integer, RoutineList> routineMap = new LinkedHashMap<>();

        for (RoutineListView row : routineList) {
            RoutineList routine = routineMap.computeIfAbsent(row.getRoutineId(),
                    id -> new RoutineList(
                            id,
                            row.getRoutineName(),
                            row.getRoutineDescription(),
                            row.getStaffFirstName(),
                            row.getStaffLastName(),
                            new HashSet<>()
                    )
            );
            routine.exercise.add(new RoutineList.Exercise(
                    row.getExerciseId(),
                    row.getExerciseName(),
                    row.getRep(),
                    row.getSet(),
                    row.getDay()
            ));
        }

        return new ArrayList<>(routineMap.values());
    }

    @Transactional
    public Routine newRoutine(NewRoutine newRoutine) {
        Staff staff = staffRepository.findById(newRoutine.getStaff_id())
                .orElseThrow(() -> new IllegalArgumentException("Staff not found"));

        Routine routine = new Routine(
                newRoutine.getName(),
                newRoutine.getDescription(),
                staff
        );

        for (NewRoutine.routine_exercise routineExercise : newRoutine.getRoutine_exercises()) {
            Exercise exercise = exerciseRepository.findById(routineExercise.getExercise_id())
                    .orElseThrow(() -> new IllegalArgumentException("Exercise not found"));

            routine.getExercises().add(new Routine_exercises(
                    exercise,
                    routineExercise.getRep(),
                    routineExercise.getSet(),
                    routineExercise.getDay()
            ));
        }

        if (newRoutine.getPatient_routine() != null) {
            patientRoutineService.newPatientRoutine(newRoutine.getPatient_routine());
        }

        return routineRepository.save(routine);
    }

    @Transactional
    public void deleteRoutine(Integer routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new IllegalArgumentException("Routine not found with id: " + routineId));
        routineRepository.delete(routine);
    }
}
