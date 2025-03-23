package com.example.elderexserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {
    @Autowired
    private PatientsRepository patientsRepository;

    @GetMapping("/")
    public List<Patients> getAllPatients() {
        return patientsRepository.findAll();
    }
}
