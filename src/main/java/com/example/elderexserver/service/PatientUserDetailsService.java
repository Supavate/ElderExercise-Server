package com.example.elderexserver.service;

import com.example.elderexserver.data.patient.DTO.PatientAuth;
import com.example.elderexserver.data.patient.Patient;
import com.example.elderexserver.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PatientUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(PatientUserDetailsService.class);

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        logger.debug("Loading patient by identifier: {}", identifier);

        Patient patient = patientRepository.findByEmail(identifier);

        logger.debug("Patient found: {} (ID: {}, Citizen ID: {})",
                patient.getEmail(), patient.getId(), patient.getCitizenId());

        return new PatientAuth(patient);
    }

    public UserDetails loadPatientById(Integer patientId) throws UsernameNotFoundException {
        logger.debug("Loading patient by ID: {}", patientId);

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> {
                    logger.warn("Patient not found with ID: {}", patientId);
                    return new UsernameNotFoundException("Patient not found with ID: " + patientId);
                });

        return new PatientAuth(patient);
    }
}
