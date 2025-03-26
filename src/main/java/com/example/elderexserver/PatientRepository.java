package com.example.elderexserver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query(value = "select * from Patient p where p.caretaker=:id", nativeQuery = true)
    List<Patient> findByCaretaker(int id);

    @Query(value = "SELECT *, TIMESTAMPDIFF(YEAR, Date_of_Birth, CURDATE()) AS Age FROM Patient;", nativeQuery = true)
    List<Object> getPatientsAge();
}
