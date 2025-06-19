package com.example.elderexserver.repository;

import com.example.elderexserver.data.patient.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NationalityRepository extends JpaRepository<Nationality, Integer> {
}
