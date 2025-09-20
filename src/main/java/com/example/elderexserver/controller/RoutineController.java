package com.example.elderexserver.controller;

import com.example.elderexserver.data.routine.DTO.NewRoutine;
import com.example.elderexserver.data.routine.DTO.RoutineList;
import com.example.elderexserver.service.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routine")
public class RoutineController {
    @Autowired
    private RoutineService routineService;

    @GetMapping("/list")
    public ResponseEntity<List<RoutineList>> getAllRoutines() {
        List<RoutineList> routineLists = routineService.getRoutineList();
        if (routineLists.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(routineLists);
    }

    @PostMapping("/new")
    public ResponseEntity<String> newRoutine(@RequestBody NewRoutine newRoutine) {
        try {
            routineService.newRoutine(newRoutine);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error registering routine: " + e.getMessage());
        }
    }

    @GetMapping("/list/{routineId}")
    public ResponseEntity<RoutineList> getRoutineListById(@PathVariable Integer routineId) {
        RoutineList routineList = routineService.getRoutineListById(routineId);
        if (routineList == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(routineList);
    }
}
