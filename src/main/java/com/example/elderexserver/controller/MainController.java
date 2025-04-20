package com.example.elderexserver.controller;

import com.example.elderexserver.data.patient.DTO.PatientWithAge;
import com.example.elderexserver.data.exercise.Actual_Exercise;
import com.example.elderexserver.data.exercise.Actual_Exercise_Detail;
import com.example.elderexserver.data.exercise.Exercise;
import com.example.elderexserver.data.exercise.Routine;
import com.example.elderexserver.data.patient.Allergy;
import com.example.elderexserver.data.patient.Patient;
import com.example.elderexserver.data.patient.Patient_Routine;
import com.example.elderexserver.data.patient.Status;
import com.example.elderexserver.repository.*;
import com.example.elderexserver.data.staff.Role;
import com.example.elderexserver.data.staff.Staff;
import com.example.elderexserver.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

//    //Patient
//    @Autowired
//    private PatientRepository patientRepository;
//    private final PatientService patientService;
//
//    @GetMapping("/pat")
//    public List<Patient> getAllPatients() {
//        return patientRepository.findAll();
//    }
//
//    @GetMapping("/pat/{id}")
//    public Patient getPatientById(@PathVariable int id) {return patientRepository.findById(id);}
//
//    @GetMapping("/patAge")
//    public List<PatientWithAge> getAllPatientsWithAge() {return patientService.getPatientsWithAge();}
//
//    @GetMapping("/pat/staff/{id}")
//    public List<Patient> getPatientsByStaff(@PathVariable int id) {return patientRepository.findByCaretaker(id);}

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

    @GetMapping("/staff")
    public List<Staff> getAllStaff() {return staffRepository.findAll();}

    @GetMapping("/staff/{id}")
    public Staff getStaffById(@PathVariable int id) {return staffRepository.findById(id).get();}

    @GetMapping("/staff/super/{id}")
    public List<Staff> getStaffBySupervisor(@PathVariable int id) {return staffRepository.findBySupervisor(id);}

    //Role
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/role")
    public List<Role> getAllRoles() {return roleRepository.findAll();}

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

    @Autowired
    PatientRoutineRepository patientRoutineRepository;

    @GetMapping("/patrou")
    public  List<Patient_Routine> getAllPatientRoutine() {
        return patientRoutineRepository.findAll();
    }

    @Autowired
    ActualExerciseRepository actualExerciseRepository;

    @GetMapping("/acte")
    public List<Actual_Exercise> getAllActualExercise() {return actualExerciseRepository.findAll();}

    @Autowired
    ActualExerciseDetailRepository actualExerciseDetailRepository;

    @GetMapping("/acted")
    public List<Actual_Exercise_Detail> getAllActualExerciseDetails() {return actualExerciseDetailRepository.findAll();}

}
