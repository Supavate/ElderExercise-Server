package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.Exercise;
import com.example.elderexserver.data.exercise.Week_Day;
import com.example.elderexserver.data.routine.DTO.NewRoutine;
import com.example.elderexserver.data.routine.DTO.RoutineList;
import com.example.elderexserver.data.routine.DTO.RoutineListView;
import com.example.elderexserver.data.routine.Routine;
import com.example.elderexserver.data.routine.Routine_exercises;
import com.example.elderexserver.data.staff.Staff;
import com.example.elderexserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RoutineService {

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private RoutineExerciseRepository routineExerciseRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private WeekDayRepository weekDayRepository;

    @Autowired
    private StaffRepository staffRepository;

    public RoutineList findRoutineListById(Integer routineId) {
        List<RoutineListView> routineListView = routineRepository.findRoutineListById(routineId);

        RoutineListView firstRow = routineListView.getFirst();
        RoutineList routine = new RoutineList(
                firstRow.getRoutineId(),
                firstRow.getRoutineName(),
                firstRow.getRoutineDescription(),
                firstRow.getStaffFirstName(),
                firstRow.getStaffLastName(),
                new HashSet<>()
        );

        for (RoutineListView row : routineListView) {

            RoutineList.Exercise exercise = routine.getExercise().stream()
                    .filter(e -> e.exerciseId.equals(row.getExerciseId()))
                    .findFirst().orElseGet(() -> {
                        RoutineList.Exercise newExercise = new RoutineList.Exercise(
                                row.getExerciseId(),
                                row.getExerciseName(),
                                new HashSet<>()
                        );
                        routine.getExercise().add(newExercise);
                        return newExercise;
                    });
            if (row.getDayId() != null && exercise.getDay().stream().noneMatch(day -> day.dayId.equals(row.getDayId()))) {
                exercise.getDay().add((new RoutineList.Day(
                        row.getDayId(),
                        row.getRep()
                )));
            }
        }

        return routine;
    }

    public List<RoutineList> findRoutineList() {
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

            RoutineList.Exercise exercise = routine.getExercise().stream()
                    .filter(e -> e.getExerciseId().equals(row.getExerciseId()))
                    .findFirst().orElseGet(() -> {
                        RoutineList.Exercise newExercise = new RoutineList.Exercise(
                                row.getExerciseId(),
                                row.getExerciseName(),
                                new HashSet<>()
                        );
                        routine.getExercise().add(newExercise);
                        return newExercise;
                    });

            if (row.getDayId() != null && exercise.getDay().stream().noneMatch(x -> x.getDayId().equals(row.getDayId()))) {
                exercise.getDay().add(new RoutineList.Day(row.getDayId(), row.getRep()));
            }
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

        routine = routineRepository.save(routine);

        for (NewRoutine.routine_exercise routineExerciseRoutine : newRoutine.getRoutine_exercises()) {
            Exercise exercise = exerciseRepository.findById(routineExerciseRoutine.getExercise_id())
                    .orElseThrow(() -> new IllegalArgumentException("Exercise not found"));

            Week_Day weekDay = weekDayRepository.findById(routineExerciseRoutine.getWeek_day_id())
                    .orElseThrow(() -> new IllegalArgumentException("Week day not found"));

            Routine_exercises routineExercises = new Routine_exercises(
                    routine,
                    exercise,
                    weekDay,
                    routineExerciseRoutine.getRep()
            );

            routineExercises.setRoutine(routine);
            routineExerciseRepository.save(routineExercises);
        }

        return routine;
    }
}
