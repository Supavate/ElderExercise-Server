package com.example.elderexserver.repository;

import com.example.elderexserver.data.patient.Food_Allergy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodAllergyRepository extends JpaRepository<Food_Allergy, Integer> {
}
