package com.example.elderexserver.repository;

import com.example.elderexserver.data.patient.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Gender, Integer> {
}
