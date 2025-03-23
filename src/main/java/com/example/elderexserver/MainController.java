package com.example.elderexserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {
    @Autowired
    private PatientsRepository patientsRepository;

    @GetMapping("/pat")
    public List<Patients> getAllPatients() {
        return patientsRepository.findAll();
    }

    @GetMapping("/{id}")
    public Patients getPatientById(@PathVariable int id) {return patientsRepository.findById(id).get();}

    @GetMapping("/pat/{id}")
    public List<Patients> getPatientByStaffID(@PathVariable int id) {return patientsRepository.getPatientsByStaff(id);}

}
