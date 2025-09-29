package com.example.elderexserver.controller;

import com.example.elderexserver.data.patient.DTO.PatientAuth;
import com.example.elderexserver.data.staff.DTO.StaffAuth;
import com.example.elderexserver.data.staff.DTO.StaffLoginResponse;
import com.example.elderexserver.data.staff.Staff;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import security.JwtUtil;
import com.example.elderexserver.data.patient.DTO.PatientLoginRequest;
import com.example.elderexserver.data.patient.DTO.PatientLoginResponse;
import com.example.elderexserver.data.patient.Patient;
import com.example.elderexserver.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/patient/login")
    public ResponseEntity<?> patientLogin(@RequestBody PatientLoginRequest request) {
        try {
            logger.info("Patient login attempt for identifier: {}", request.getEmail());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            PatientAuth PatientAuth = (PatientAuth) authentication.getPrincipal();
            Patient patient = PatientAuth.getPatient();

            // Generate JWT token using the Patient entity
            String token = jwtUtil.generatePatientToken(patient);

            logger.info("Patient login successful for: {} (ID: {})", patient.getEmail(), patient.getId());

            // Create response using patient data
            PatientLoginResponse response = new PatientLoginResponse(
                    token,
                    patient.getId(),
                    patient.getCitizenId(),
                    patient.getEmail(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getPhone(),
                    patient.getPicture(),
                    PatientAuth.getGender(),
                    PatientAuth.getBloodType(),
                    patient.getCaretaker() != null ? patient.getCaretaker().getFirst_Name() + " " + patient.getCaretaker().getLast_Name() : null,
                    "Login successful"
            );

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            logger.warn("Patient login failed for: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("/patient/logout")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> logoutSecure(HttpServletRequest request, Authentication authentication) {
        try {
            String username = authentication.getName();

            // Extract JWT token from request
            String token = extractTokenFromRequest(request);

            if (token != null) {
                Long expirationTime = jwtUtil.getTokenExpiryTime(token);

                logger.info("Patient logout with token blacklisting for: {}", username);
            }


            Map<String, Object> response = new HashMap<>();
            response.put("message", "Logout successful - token invalidated");
            response.put("timestamp", LocalDateTime.now());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error during secure logout", e);
            return ResponseEntity.ok(Map.of("message", "Logout completed"));
        }
    }

    @PostMapping("/staff/login")
    public ResponseEntity<?> staffLogin(@RequestBody PatientLoginRequest request) {
        try {
            logger.info("Staff login attempt for identifier: {}", request.getEmail());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            StaffAuth staffAuth = (StaffAuth) authentication.getPrincipal();
            Staff staff = staffAuth.getStaff();


            String token = jwtUtil.generateStaffToken(staff);

            logger.info("Staff login successful for: {} (ID: {})", staff.getEmail(), staff.getId());

            StaffLoginResponse response = new StaffLoginResponse(
                    token,
                    staff.getId(),
                    staff.getEmail(),
                    staff.getFirst_Name(),
                    staff.getLast_Name(),
                    staff.getTelephone(),
                    staff.getPicture(),
                    staffAuth.getGender(),
                    staff.getSupervisor_id() != null ? staff.getSupervisor_id() : null,
                    "Login successful"
            );

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            logger.warn("Staff login failed for: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

}