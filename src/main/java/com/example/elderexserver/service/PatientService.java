package com.example.elderexserver.service;

import com.example.elderexserver.data.patient.DTOs.PatientWithAge;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<PatientWithAge> findAllWithAge() {
        return entityManager.createNativeQuery("""
            SELECT id, first_name, last_name, weight, height, TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) AS age FROM Patient
        """, "PatientWithAgeMapping")
                .getResultList();
    }
}
