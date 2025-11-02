package com.example.elderexserver.controller;

import com.example.elderexserver.data.routine.DTO.NewRoutine;
import com.example.elderexserver.data.routine.DTO.RoutineList;
import com.example.elderexserver.data.routine.Routine;
import com.example.elderexserver.service.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routine")
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineService routineService;

    @GetMapping("/list")
    public ResponseEntity<List<RoutineList>> getAllRoutines() {
        List<RoutineList> routineLists = routineService.getRoutineList();
        if (routineLists.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(routineLists);
    }

    @GetMapping("/list/{routineId}")
    public ResponseEntity<RoutineList> getRoutineListById(@PathVariable Integer routineId) {
        return routineService.getRoutineListById(routineId) != null
                ? ResponseEntity.ok(routineService.getRoutineListById(routineId))
                : ResponseEntity.notFound().build();
    }

    // TODO: Move to staff controller
    @PostMapping("/new")
    public ResponseEntity<Routine> newRoutine(@RequestBody NewRoutine newRoutine) {
        Routine createdRoutine = routineService.newRoutine(newRoutine);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoutine);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRoutine(@PathVariable Integer id) {
        routineService.deleteRoutine(id);
        return ResponseEntity.noContent().build();
    }
}
