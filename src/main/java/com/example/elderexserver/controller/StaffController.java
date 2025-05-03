package com.example.elderexserver.controller;

import com.example.elderexserver.data.staff.DTO.NewStaff;
import com.example.elderexserver.data.staff.DTO.StaffListView;
import com.example.elderexserver.data.staff.DTO.StaffProfileView;
import com.example.elderexserver.data.staff.DTO.UpdateStaff;
import com.example.elderexserver.data.staff.Staff;
import com.example.elderexserver.repository.StaffRepository;
import com.example.elderexserver.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffService staffService;

    @GetMapping("/all")
    public List<Staff> getAllStaff() {return staffRepository.findAll();}

    @GetMapping("/profile/{id}")
    public StaffProfileView getStaffProfileById(@PathVariable int id) {return staffRepository.findStaffProfileById(id);}

    @GetMapping("/list")
    public List<StaffListView> getStaffList() {return staffRepository.findStaff();}

    @GetMapping("/super/{id}")
    public List<StaffListView> getStaffBySupervisor(@PathVariable int id) {return staffRepository.findStaffBySupervisor(id);}

    @PostMapping("/new")
    public ResponseEntity<String> addNewStaff(@RequestBody NewStaff newStaff) {
        try {
            staffService.newStaff(newStaff);
            return ResponseEntity.ok("New staff added");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error registering staff: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateStaff(@RequestBody UpdateStaff updateStaff) {
        try {
            staffService.updateStaff(updateStaff);
            return ResponseEntity.ok("Staff updated");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating staff: " + e.getMessage());
        }
    }
}
