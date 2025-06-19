package com.example.elderexserver.repository;

import com.example.elderexserver.data.patient.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Integer> {
}
