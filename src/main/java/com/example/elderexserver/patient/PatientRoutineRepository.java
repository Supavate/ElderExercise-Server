package com.example.elderexserver.patient;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRoutineRepository extends JpaRepository<Patient_Routine, Integer> {
}
