package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.Actual_Exercise;
import com.example.elderexserver.data.exercise.Actual_Exercise_Detail;
import com.example.elderexserver.data.exercise.Routine;
import com.example.elderexserver.data.patient.Allergy;
import com.example.elderexserver.data.patient.Blood_Type;
import com.example.elderexserver.data.patient.Patient_Routine;
import com.example.elderexserver.data.patient.Status;
import com.example.elderexserver.repository.*;
import com.example.elderexserver.data.staff.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    //Allergy
    @Autowired
    private AllergyRepository allergyRepository;

    @GetMapping("/allergy")
    public List<Allergy> getAllAllergies() {return allergyRepository.findAll();}

    //Status
    @Autowired
    private StatusRepository statusRepository;

    @GetMapping("/status")
    public List<Status> getAllStatus() {return statusRepository.findAll();}

    //Role
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/role")
    public List<Role> getAllRoles() {return roleRepository.findAll();}

    //BloodType
    @Autowired
    private BloodTypeRepository bloodTypeRepository;

    @GetMapping("/bloodtype")
    public List<Blood_Type> getAllBloodTypes() {return bloodTypeRepository.findAll();}
}
