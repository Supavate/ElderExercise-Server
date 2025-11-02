package com.example.elderexserver.service;

import com.example.elderexserver.data.patient.Gender;
import com.example.elderexserver.data.staff.DTO.NewStaff;
import com.example.elderexserver.data.staff.DTO.StaffListView;
import com.example.elderexserver.data.staff.DTO.StaffProfileView;
import com.example.elderexserver.data.staff.DTO.UpdateStaff;
import com.example.elderexserver.data.staff.Role;
import com.example.elderexserver.data.staff.Staff;
import com.example.elderexserver.repository.GenderRepository;
import com.example.elderexserver.repository.RoleRepository;
import com.example.elderexserver.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final PasswordEncoder passwordEncoder;
    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;
    private final GenderRepository genderRepository;

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

        Optional.ofNullable(updateStaff.getFirst_name()).ifPresent(staff::setFirst_Name);
        Optional.ofNullable(updateStaff.getLast_name()).ifPresent(staff::setLast_Name);
        Optional.ofNullable(updateStaff.getEmail()).ifPresent(staff::setEmail);
        Optional.ofNullable(updateStaff.getTelephone()).ifPresent(staff::setTelephone);
        Optional.ofNullable(updateStaff.getPicture()).ifPresent(staff::setPicture);

        if (updateStaff.getDate_of_birth() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dob = LocalDate.parse(updateStaff.getDate_of_birth(), formatter);
            staff.setDate_of_birth(dob);
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

    public List<Staff> findAllStaff() {
        return staffRepository.findAll();
    }

    public StaffProfileView findStaffById(int id) {
        return staffRepository.findStaffProfileById(id);
    }

    public List<StaffListView> findStaffList() {
        return staffRepository.findStaffList();
    }

    public List<StaffListView> findStaffBySupervisor(int id) {
        return staffRepository.findStaffBySupervisor(id);
    }
}