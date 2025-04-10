package com.example.elderexserver.repository;

import com.example.elderexserver.data.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query(value = "SELECT *, TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) AS age FROM Patient;", nativeQuery = true)
    List<Patient> findAll();

    @Query(value = "SELECT *, TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) AS age FROM Patient p WHERE p.id=:id;", nativeQuery = true)
    Patient findById(int id);

    @Query(value = "select * from Patient p WHERE p.caretaker_id=:id", nativeQuery = true)
    List<Patient> findByCaretaker(int id);

    @Query(value = "SELECT id, first_name, last_name, weight, height, TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) AS age FROM Patient p WHERE p.id=:id;", nativeQuery = true)
    Patient findByIdWithAge(int id);
}
