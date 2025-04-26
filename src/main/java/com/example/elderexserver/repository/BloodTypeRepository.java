package com.example.elderexserver.repository;

import com.example.elderexserver.data.patient.Blood_Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodTypeRepository extends JpaRepository<Blood_Type, Integer> {
}
