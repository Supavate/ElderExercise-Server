package com.example.elderexserver.service;

import com.example.elderexserver.data.patient.Gender;
import com.example.elderexserver.data.staff.DTO.NewStaff;
import com.example.elderexserver.data.staff.Role;
import com.example.elderexserver.data.staff.Staff;
import com.example.elderexserver.repository.GenderRepository;
import com.example.elderexserver.repository.RoleRepository;
import com.example.elderexserver.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GenderRepository genderRepository;

    public Staff newStaff(NewStaff newStaff) {
        Gender gender = genderRepository.findById(newStaff.getGender_id())
                .orElseThrow(() -> new RuntimeException("Gender not found"));
        Role role = roleRepository.findById(newStaff.getRole_id())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Staff staff = new Staff(
                newStaff.getFirstName(),
                newStaff.getLastName(),
                gender,
                newStaff.getEmail(),
                LocalDate.parse(newStaff.getBirthday(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                newStaff.getEmail(),     // temporarily use email as username
                newStaff.getPassword(),
                role,
                newStaff.getImage()
        );

        staff.setTelephone("N/A");

        return staffRepository.save(staff);
    }
}
