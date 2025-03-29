package com.example.elderexserver.repository;

import com.example.elderexserver.data.patient.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergyRepository extends JpaRepository<Allergy, Integer> {
}
