package com.example.elderexserver.repository;

import com.example.elderexserver.data.patient.Patient_Routine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRoutineRepository extends JpaRepository<Patient_Routine, Integer> {
}
