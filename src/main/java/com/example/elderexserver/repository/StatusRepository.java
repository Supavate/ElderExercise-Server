package com.example.elderexserver.repository;

import com.example.elderexserver.data.patient.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}
