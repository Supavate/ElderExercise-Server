package com.example.elderexserver.data.staff.DTO;

import com.example.elderexserver.data.staff.Staff;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

public class StaffAuth implements UserDetails {
    private final Staff staff;
    //private final StaffAccountStatus accountStatus;

    public StaffAuth(Staff staff) {
        this.staff = staff;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_STAFF"));
    }

    @Override
    public String getPassword() {
        return staff.getPassword();
    }

    @Override
    public String getUsername() {
        return staff.getUsername();
    }

    public Staff getStaff() {
        return staff;
    }

    public String getFirstName() {
        return staff.getFirst_Name();
    }

    public String getLastName() {
        return staff.getLast_Name();
    }

    public String getEmail() {
        return staff.getEmail();
    }

    public String getPhone() {
        return staff.getTelephone();
    }

    public LocalDate getDateOfBirth() {
        return staff.getDate_of_birth();
    }

    public String getGender() {
        return staff.getGender().getName();
    }
}
