package com.example.elderexserver.controller;

import com.example.elderexserver.data.staff.DTOs.StaffListView;
import com.example.elderexserver.data.staff.DTOs.StaffProfileView;
import com.example.elderexserver.data.staff.Staff;
import com.example.elderexserver.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;

    @GetMapping("/all")
    public List<Staff> getAllStaff() {return staffRepository.findAll();}

    @GetMapping("/profile/{id}")
    public StaffProfileView getStaffProfileById(@PathVariable int id) {return staffRepository.findStaffProfileById(id);}

    @GetMapping("/list")
    public List<StaffListView> getStaffList() {return staffRepository.findStaff();}

    @GetMapping("/super/{id}")
    public List<StaffListView> getStaffBySupervisor(@PathVariable int id) {return staffRepository.findStaffBySupervisor(id);}
}
