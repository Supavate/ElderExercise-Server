package com.example.elderexserver.data.patient.DTO;

import com.example.elderexserver.data.patient.Patient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

public class PatientAuth implements UserDetails {

    private final Patient patient;
    private final PatientAccountStatus accountStatus;

    public PatientAuth(Patient patient) {
        this.patient = patient;
        this.accountStatus = determineAccountStatus(patient);
    }

    public PatientAuth(Patient patient, PatientAccountStatus accountStatus) {
        this.patient = patient;
        this.accountStatus = accountStatus;
    }


    private PatientAccountStatus determineAccountStatus(Patient patient) {
        // Add your business logic here
        boolean isEnabled = true; // Default enabled
        boolean isLocked = false;
        boolean isExpired = false;
        boolean isCredentialsExpired = false;

        if (patient.getEmail() == null || patient.getEmail().trim().isEmpty()) {
            isEnabled = false;
        }

        // 2. Check if patient is too young (e.g., under 18)
        if (patient.getDateOfBirth() != null) {
            int age = java.time.Period.between(patient.getDateOfBirth(), LocalDate.now()).getYears();
            if (age < 18) {
                isLocked = true; // Require guardian approval
            }
        }

        // 3. Check if patient has been discharged recently (example rule)
        // You could check patient status history here

        // 4. Password expiry logic (if you track last password change)
        // LocalDateTime lastPasswordChange = getLastPasswordChange(patient);
        // if (lastPasswordChange != null && lastPasswordChange.isBefore(LocalDateTime.now().minusMonths(6))) {
        //     isCredentialsExpired = true;
        // }

        return new PatientAccountStatus(isEnabled, isLocked, isExpired, isCredentialsExpired);
    }

    // ============ USERDETAILS IMPLEMENTATION ============

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_PATIENT"));
    }

    @Override
    public String getPassword() {
        return patient.getPassword();
    }

    @Override
    public String getUsername() {
        return patient.getEmail() != null ? patient.getEmail() : patient.getCitizenId();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountStatus.isAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !accountStatus.isCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return accountStatus.isEnabled();
    }

    // ============ PATIENT ACCESS METHODS ============

    public Patient getPatient() {
        return patient;
    }

    public Integer getPatientId() {
        return patient.getId();
    }

    public String getCitizenId() {
        return patient.getCitizenId();
    }

    public String getFirstName() {
        return patient.getFirstName();
    }

    public String getLastName() {
        return patient.getLastName();
    }

    public String getFullName() {
        return (patient.getFirstName() != null ? patient.getFirstName() : "") +
                (patient.getLastName() != null ? " " + patient.getLastName() : "").trim();
    }

    public String getEmail() {
        return patient.getEmail();
    }

    public String getPhone() {
        return patient.getPhone();
    }

    public LocalDate getDateOfBirth() {
        return patient.getDateOfBirth();
    }

    public int getAge() {
        if (patient.getDateOfBirth() != null) {
            return java.time.Period.between(patient.getDateOfBirth(), LocalDate.now()).getYears();
        }
        return 0;
    }

    public String getBloodType() {
        return patient.getBloodType().getType();
    }

    public String getGender() {
        return patient.getGender().getName();
    }

    public PatientAccountStatus getAccountStatus() {
        return accountStatus;
    }

    @Override
    public int hashCode() {
        return patient.getId() != null ? patient.getId().hashCode() : 0;
    }
}
