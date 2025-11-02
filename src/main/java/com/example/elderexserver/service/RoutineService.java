package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.Exercise;
import com.example.elderexserver.data.patient.Patient;
import com.example.elderexserver.data.routine.DTO.NewRoutine;
import com.example.elderexserver.data.routine.DTO.RoutineList;
import com.example.elderexserver.data.routine.DTO.RoutineListView;
import com.example.elderexserver.data.routine.Patient_Routine;
import com.example.elderexserver.data.routine.Routine;
import com.example.elderexserver.data.routine.Routine_exercises;
import com.example.elderexserver.data.staff.Staff;
import com.example.elderexserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class RoutineService {

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PatientRoutineService patientRoutineService;

    public RoutineList getRoutineListById(Integer routineId) {
        List<RoutineListView> routineListView = routineRepository.findRoutineListById(routineId);

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

            RoutineList.Exercise exercise = new RoutineList.Exercise(
                    row.getExerciseId(),
                    row.getExerciseName(),
                    row.getRep(),
                    row.getSet(),
                    row.getDay()
            );

            routine.exercise.add(exercise);
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

            RoutineList.Exercise exercise = new RoutineList.Exercise(
                    row.getExerciseId(),
                    row.getExerciseName(),
                    row.getRep(),
                    row.getSet(),
                    row.getDay()
            );

            routine.exercise.add(exercise);
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

        List<NewRoutine.routine_exercise> routineExercises = newRoutine.getRoutine_exercises();
        for (NewRoutine.routine_exercise routineExercise : routineExercises) {
            Exercise exercise = exerciseRepository.findById(routineExercise.getExercise_id())
                    .orElseThrow(() -> new IllegalArgumentException("Exercise not found"));

            Routine_exercises routine_exercise = new Routine_exercises(
                    routine,
                    exercise,
                    routineExercise.getRep(),
                    routineExercise.getSet(),
                    routineExercise.getDay()
            );

            routine.getExercises().add(routine_exercise);
        }

        if (newRoutine.getPatient_routine() != null) {
            patientRoutineService.newPatientRoutine(newRoutine.getPatient_routine());
        }

        return routineRepository.save(routine);
    }

    @Transactional
    public String deleteRoutine(Integer routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new IllegalArgumentException("Routine not found with id: " + routineId));

        routineRepository.delete(routine);

        return "Routine deleted successfully";
    }
}
