package com.example.elderexserver.repository;

import com.example.elderexserver.data.patient.Drug_Allergy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrugAllergyRepository extends JpaRepository<Drug_Allergy, Integer> {
}
