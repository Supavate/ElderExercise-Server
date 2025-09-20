package com.example.elderexserver.controller;

import com.example.elderexserver.data.staff.DTO.NewStaff;
import com.example.elderexserver.data.staff.DTO.StaffListView;
import com.example.elderexserver.data.staff.DTO.StaffProfileView;
import com.example.elderexserver.data.staff.DTO.UpdateStaff;
import com.example.elderexserver.data.staff.Staff;
import com.example.elderexserver.repository.StaffRepository;
import com.example.elderexserver.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffService staffService;

    @GetMapping("/all")
    public ResponseEntity<List<Staff>> getAllStaff() {
        List<Staff> staffList = staffRepository.findAll();
        if (staffList.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(staffList);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<StaffProfileView> getStaffProfileById(@PathVariable int id) {
        StaffProfileView staff = staffRepository.findStaffProfileById(id);
        if (staff == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(staff);
    }

    @GetMapping("/list")
    public ResponseEntity<List<StaffListView>> getStaffList() {
        List<StaffListView> staffListViews = staffRepository.findStaff();
        if (staffListViews.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(staffListViews);
    }

    @GetMapping("/super/{id}")
    public ResponseEntity<List<StaffListView>> getStaffBySupervisor(@PathVariable int id) {
        List<StaffListView> staffListViews = staffRepository.findStaffBySupervisor(id);
        if (staffListViews.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(staffListViews);
    }

    @PostMapping("/new")
    public ResponseEntity<String> addNewStaff(@RequestBody NewStaff newStaff) {
        try {
            staffService.newStaff(newStaff);
            return ResponseEntity.ok("New staff added");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error registering staff: " + e.getMessage());
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateStaff(@RequestBody UpdateStaff updateStaff) {
        try {
            staffService.updateStaff(updateStaff);
            return ResponseEntity.ok("Staff updated");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating staff: " + e.getMessage());
        }
    }
}
