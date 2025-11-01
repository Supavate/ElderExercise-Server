package com.example.elderexserver.controller;

import com.example.elderexserver.data.routine.DTO.NewRoutine;
import com.example.elderexserver.data.routine.DTO.RoutineList;
import com.example.elderexserver.service.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routine")
public class RoutineController {
    @Autowired
    private RoutineService routineService;

    @GetMapping("/list")
    public ResponseEntity<List<RoutineList>> getAllRoutines() {
        try {
            List<RoutineList> routineLists = routineService.getRoutineList();
            if (routineLists.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(routineLists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/list/{routineId}")
    public ResponseEntity<RoutineList> getRoutineListById(@PathVariable Integer routineId) {
        try {
            RoutineList routineList = routineService.getRoutineListById(routineId);
            if (routineList == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(routineList);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> newRoutine(@RequestBody NewRoutine newRoutine) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(routineService.newRoutine(newRoutine));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error registering routine: " + e.getMessage());
        }
    }
}
