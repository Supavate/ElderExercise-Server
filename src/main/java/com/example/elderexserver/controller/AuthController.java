package com.example.elderexserver.controller;

import com.example.elderexserver.data.staff.DTO.StaffLoginRequest;
import com.example.elderexserver.data.staff.DTO.StaffLoginResponse;
import com.example.elderexserver.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.example.elderexserver.data.patient.DTO.PatientLoginRequest;
import com.example.elderexserver.data.patient.DTO.PatientLoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/patient/login")
    public ResponseEntity<PatientLoginResponse> patientLogin(@RequestBody PatientLoginRequest request) {
        return ResponseEntity.ok(authService.patientLogin(request));
    }

    @PostMapping("/staff/login")
    public ResponseEntity<StaffLoginResponse> staffLogin(@RequestBody StaffLoginRequest request) {
        return ResponseEntity.ok(authService.staffLogin(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logoutSecure(HttpServletRequest request, Authentication authentication) {
        return ResponseEntity.ok(authService.logout(request, authentication));
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authHeader) {
        authService.validateToken(authHeader);
        return ResponseEntity.ok("Token validated");
    }
}