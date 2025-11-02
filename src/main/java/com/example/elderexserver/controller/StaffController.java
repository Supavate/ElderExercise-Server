package com.example.elderexserver.controller;

import com.example.elderexserver.data.staff.DTO.NewStaff;
import com.example.elderexserver.data.staff.DTO.StaffListView;
import com.example.elderexserver.data.staff.DTO.StaffProfileView;
import com.example.elderexserver.data.staff.DTO.UpdateStaff;
import com.example.elderexserver.data.staff.Staff;
import com.example.elderexserver.repository.StaffRepository;
import com.example.elderexserver.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;

    @GetMapping("/all")
    public ResponseEntity<List<Staff>> getAllStaff() {
        return ResponseEntity.ok(staffService.findAllStaff());
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<StaffProfileView> getStaffProfileById(@PathVariable int id) {
        return ResponseEntity.ok(staffService.findStaffById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<StaffListView>> getStaffList() {
        return ResponseEntity.ok(staffService.findStaffList());
    }

    @GetMapping("/super/{id}")
    public ResponseEntity<List<StaffListView>> getStaffBySupervisor(@PathVariable int id) {
        return ResponseEntity.ok(staffService.findStaffBySupervisor(id));
    }

    @PostMapping("/new")
    public ResponseEntity<Staff> addNewStaff(@RequestBody NewStaff newStaff) {
        return ResponseEntity.ok(staffService.newStaff(newStaff));
    }

    @PatchMapping("/update")
    public ResponseEntity<Staff> updateStaff(@RequestBody UpdateStaff updateStaff) {
        return ResponseEntity.ok(staffService.updateStaff(updateStaff));
    }
}