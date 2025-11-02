package com.example.elderexserver.controller;

import com.example.elderexserver.data.patient.DTO.PatientAuth;
import com.example.elderexserver.data.staff.DTO.StaffAuth;
import com.example.elderexserver.data.staff.DTO.StaffLoginRequest;
import com.example.elderexserver.data.staff.DTO.StaffLoginResponse;
import com.example.elderexserver.data.staff.Staff;
import com.example.elderexserver.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
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