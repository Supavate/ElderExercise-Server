package com.example.elderexserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

@RestController
public class MainController {
    //Patient
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/pat")
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @GetMapping("/pat/{id}")
    public Patient getPatientById(@PathVariable int id) {return patientRepository.findById(id);}

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

    //Staff
    @Autowired
    private StaffRepository staffRepository;

    @GetMapping("/pat/staff/{id}")
    public List<Patient> getPatientsByStaff(@PathVariable int id) {return patientRepository.findByCaretaker(id);}

    @GetMapping("/staff")
    public List<Staff> getAllStaff() {return staffRepository.findAll();}

    @GetMapping("/staff/{id}")
    public Staff getStaffById(@PathVariable int id) {return staffRepository.findById(id).get();}

    @GetMapping("/staff/super/{id}")
    public List<Staff> getStaffBySupervisor(@PathVariable int id) {return staffRepository.findBySupervisor(id);}

    //Routine
    @Autowired
    RoutineRepository routineRepository;

    @GetMapping("/routine")
    public List<Routine> getAllRoutines() {return routineRepository.findAll();}

    //Exercise
    @Autowired
    ExerciseRepository exerciseRepository;
    @GetMapping("/exe")
    public List<Exercise> getAllExercises() {return exerciseRepository.findAll();}
}
