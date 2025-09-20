package com.example.elderexserver.service;

import com.example.elderexserver.data.patient.Gender;
import com.example.elderexserver.data.staff.DTO.NewStaff;
import com.example.elderexserver.data.staff.DTO.UpdateStaff;
import com.example.elderexserver.data.staff.Role;
import com.example.elderexserver.data.staff.Staff;
import com.example.elderexserver.repository.GenderRepository;
import com.example.elderexserver.repository.RoleRepository;
import com.example.elderexserver.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class StaffService {
    @Autowired
    private PasswordEncoder passwordEncoder;

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

        String hashedPassword = passwordEncoder.encode(newStaff.getPassword());

        Staff staff = new Staff(
                newStaff.getFirstName(),
                newStaff.getLastName(),
                gender,
                newStaff.getEmail(),
                LocalDate.parse(newStaff.getBirthday(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                newStaff.getEmail(),
                hashedPassword,
                role,
                newStaff.getPicture(),
                newStaff.getTelephone()
        );

        return staffRepository.save(staff);
    }

    public Staff updateStaff(UpdateStaff updateStaff) {
        Staff staff = staffRepository.findById(updateStaff.getId())
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        if (updateStaff.getFirst_name() != null) {
            staff.setFirst_Name(updateStaff.getFirst_name());
        }

        if (updateStaff.getLast_name() != null) {
            staff.setLast_Name(updateStaff.getLast_name());
        }

        if (updateStaff.getEmail() != null) {
            staff.setEmail(updateStaff.getEmail());
        }

        if (updateStaff.getTelephone() != null) {
            staff.setTelephone(updateStaff.getTelephone());
        }

        if (updateStaff.getPicture() != null) {
            staff.setPicture(updateStaff.getPicture());
        }

        if (updateStaff.getDate_of_birth() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            try {
                LocalDate dob = LocalDate.parse(updateStaff.getDate_of_birth(), formatter);
                staff.setDate_of_birth(dob);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format. Expected dd/MM/yyyy");
            }
        }

        if (updateStaff.getRole_id() != null) {
            Role role = roleRepository.findById(updateStaff.getRole_id())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            staff.setRole(role);
        }

        if (updateStaff.getGender_id() != null) {
            Gender gender = genderRepository.findById(updateStaff.getGender_id())
                    .orElseThrow(() -> new RuntimeException("Gender not found"));
            staff.setGender(gender);
        }

        return staffRepository.save(staff);
    }
}
