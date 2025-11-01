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
    private StaffService staffService;

    @GetMapping("/all")
    public ResponseEntity<List<Staff>> getAllStaff() {
        try {
            return ResponseEntity.ok(staffService.findAllStaff());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<StaffProfileView> getStaffProfileById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(staffService.findStaffById(id));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<StaffListView>> getStaffList() {
        try {
            return ResponseEntity.ok(staffService.findStaffList());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/super/{id}")
    public ResponseEntity<List<StaffListView>> getStaffBySupervisor(@PathVariable int id) {
        try {
            return ResponseEntity.ok(staffService.findStaffBySupervisor(id));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<?> addNewStaff(@RequestBody NewStaff newStaff) {
        try {
            return ResponseEntity.ok(staffService.newStaff(newStaff));
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
