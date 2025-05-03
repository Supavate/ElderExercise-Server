package com.example.elderexserver.repository;

import com.example.elderexserver.data.exercise.Week_Day;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekDayRepository extends JpaRepository<Week_Day, Integer> {
}
