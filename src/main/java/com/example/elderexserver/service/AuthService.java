package com.example.elderexserver.service;

import com.example.elderexserver.data.patient.DTO.PatientAuth;
import com.example.elderexserver.data.patient.DTO.PatientLoginRequest;
import com.example.elderexserver.data.patient.DTO.PatientLoginResponse;
import com.example.elderexserver.data.patient.Patient;
import com.example.elderexserver.data.staff.DTO.StaffAuth;
import com.example.elderexserver.data.staff.DTO.StaffLoginRequest;
import com.example.elderexserver.data.staff.DTO.StaffLoginResponse;
import com.example.elderexserver.data.staff.Staff;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import security.JwtUtil;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public PatientLoginResponse patientLogin(PatientLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        PatientAuth patientAuth = (PatientAuth) authentication.getPrincipal();
        Patient patient = patientAuth.getPatient();

        String token = jwtUtil.generatePatientToken(patient);

        return new PatientLoginResponse(
                token,
                patient.getId(),
                patient.getCitizenId(),
                patient.getEmail(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPhone(),
                patient.getPicture(),
                patientAuth.getGender(),
                patientAuth.getBloodType(),
                patient.getCaretaker() != null ? patient.getCaretaker().getFirst_Name() + " " + patient.getCaretaker().getLast_Name() : null,
                "Login successful"
        );
    }

    public StaffLoginResponse staffLogin(StaffLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        StaffAuth staffAuth = (StaffAuth) authentication.getPrincipal();
        Staff staff = staffAuth.getStaff();

        String token = jwtUtil.generateStaffToken(staff);

        return new StaffLoginResponse(
                token,
                staff.getId(),
                staff.getEmail(),
                staff.getFirst_Name(),
                staff.getLast_Name(),
                staff.getTelephone(),
                staff.getPicture(),
                staffAuth.getGender(),
                staff.getSupervisor_id(),
                "Login successful"
        );
    }

    public Map<String, Object> logout(HttpServletRequest request, Authentication authentication) {
        String username = authentication.getName();
        String token = extractTokenFromRequest(request);

        if (token != null) {
            Long expirationTime = jwtUtil.getTokenExpiryTime(token);
            // TODO: Optionally blacklist token
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Logout successful - token invalidated");
        response.put("timestamp", LocalDateTime.now());
        return response;
    }

    public void validateToken(String authHeader) {
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new BadCredentialsException("Missing Bearer token");
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            throw new BadCredentialsException("Token is invalid or expired");
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

