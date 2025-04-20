package com.example.elderexserver.repository;

import com.example.elderexserver.data.patient.Patient;
import com.example.elderexserver.data.patient.DTO.PatientWithAgeView;
import com.example.elderexserver.data.patient.DTO.PatientWithAllergiesView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query(value = "SELECT *, TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) AS age FROM Patient p WHERE p.id=:id;", nativeQuery = true)
    Patient findById(int id);

    @Query(value = "select * from Patient p WHERE p.caretaker_id=:id", nativeQuery = true)
    List<Patient> findByCaretaker(int id);

    @Query(value = "SELECT id, first_name, last_name, weight, height, TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) AS age FROM patient;", nativeQuery = true)
    List<PatientWithAgeView> findAllWithAge();

    @Query(value = """
        Select p.id, first_name, last_name, weight, height, GROUP_CONCAT(a.name) AS allergies from Patient p
        LEFT JOIN patient_allergy ON patient_allergy.patient_id = p.id
        LEFT JOIN allergy a ON a.id = patient_allergy.allergy_id
        GROUP BY p.id;
    """, nativeQuery = true)
    List<PatientWithAllergiesView> findAllWithAllergies();
}
