package com.example.elderexserver.controller;

import com.example.elderexserver.data.patient.DTO.PatientAuth;
import jakarta.servlet.http.HttpServletRequest;
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
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/logout")
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

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

}