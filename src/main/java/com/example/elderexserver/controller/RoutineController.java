package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.DTO.RoutineList;
import com.example.elderexserver.service.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/routine")
public class RoutineController {
    @Autowired
    private RoutineService routineService;

    @GetMapping("/list")
    public List<RoutineList> getAllRoutines() {
        return routineService.findRoutineList();
    }
}
