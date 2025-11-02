package com.example.elderexserver.service;

import com.example.elderexserver.data.patient.DTO.PatientAccountStatus;
import com.example.elderexserver.data.patient.DTO.PatientAuth;
import com.example.elderexserver.data.patient.Patient;
import com.example.elderexserver.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientAuthService {
    private static final Logger logger = LoggerFactory.getLogger(PatientAuthService.class);

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    public PatientAuth createPatientAuthDTO(Patient patient) {
        PatientAccountStatus status = evaluatePatientAccountStatus(patient);
        return new PatientAuth(patient, status);
    }

    public PatientAccountStatus evaluatePatientAccountStatus(Patient patient) {
        boolean isEnabled = true;
        boolean isLocked = false;
        boolean isExpired = false;
        boolean isCredentialsExpired = false;
        String reason = "Account is active";

        // Business Rule 1: Patient must have email for login
        if (patient.getEmail() == null || patient.getEmail().trim().isEmpty()) {
            isEnabled = false;
            reason = "Email is required for account access";
        }

        // Business Rule 2: Patient must have valid password
        else if (patient.getPassword() == null || patient.getPassword().trim().isEmpty()) {
            isEnabled = false;
            reason = "Account setup is incomplete";
        }

        //todo: Implement status
//        else if (isCurrentlyAdmitted(patient)) {
//            // You might want different rules for admitted patients
//            logger.info("Patient {} is currently admitted", patient.getId());
//        }

        else if (isAccountInactive(patient)) {
            isExpired = true;
            reason = "Account expired due to inactivity";
        }

        else if (isPasswordExpired(patient)) {
            isCredentialsExpired = true;
            reason = "Password has expired and must be changed";
        }

        return new PatientAccountStatus(isEnabled, isLocked, isExpired,
                isCredentialsExpired, reason);
    }

    //todo: Implement status
//    private boolean isCurrentlyAdmitted(Patient patient) {
//        return patient.getPatientStatuses();
//    }

    private boolean isAccountInactive(Patient patient) {
        // Example: Account is inactive if no login for 1 year
        // You'd need to track last login timestamp
        // For now, returning false
        return false;
    }

    private boolean isPasswordExpired(Patient patient) {
        // Example: Password expires after 6 months
        // You'd need to track password creation/change date
        // For now, returning false
        return false;
    }

    /**
     * Validate patient login credentials
     */
    public boolean validatePatientCredentials(String email, String password) {
        try {
            Patient patient = patientRepository.findByEmail(email);
            return passwordEncoder.matches(password, patient.getPassword());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean changePatientPassword(String email, String currentPassword, String newPassword) {
        try {
            Patient patient = patientRepository.findByEmail(email);

            // Verify current password
            if (!passwordEncoder.matches(currentPassword, patient.getPassword())) {
                logger.warn("Password change failed - incorrect current password for: {}", email);
                return false;
            }

            // Validate new password strength
            if (!isValidPassword(newPassword)) {
                logger.warn("Password change failed - weak password for: {}", email);
                throw new IllegalArgumentException("Password does not meet requirements");
            }

            // Update password
            patient.setPassword(passwordEncoder.encode(newPassword));
            patientRepository.save(patient);

            logger.info("Password changed successfully for patient: {}", email);
            return true;

        } catch (Exception e) {
            logger.error("Error changing password for patient: {}", email, e);
            throw new RuntimeException("Failed to change password", e);
        }
    }


    //add password rule here
    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }

}
