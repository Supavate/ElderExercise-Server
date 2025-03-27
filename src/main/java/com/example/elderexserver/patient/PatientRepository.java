package com.example.elderexserver.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query(value = "SELECT *, TIMESTAMPDIFF(YEAR, Date_of_Birth, CURDATE()) AS Age FROM Patient;", nativeQuery = true)
    List<Patient> findAll();

    @Query(value = "SELECT *, TIMESTAMPDIFF(YEAR, Date_of_Birth, CURDATE()) AS Age FROM Patient p WHERE p.id=:id;", nativeQuery = true)
    Patient findById(int id);


    @Query(value = "select *, TIMESTAMPDIFF(YEAR, Date_of_Birth, CURDATE()) AS Age from Patient p WHERE p.caretaker=:id", nativeQuery = true)
    List<Patient> findByCaretaker(int id);
}
