package com.example.elderexserver.data.exercise.DTO;

import java.time.LocalDateTime;

public interface ActualExerciseDetailListView {
    Integer getId();
    String getExerciseName();
    Integer getExerciseId();
    LocalDateTime getStartTime();
    LocalDateTime getEndTime();
    Integer getAvgHeartRate();
    Integer getHrZone1();
    Integer getHrZone2();
    Integer getHrZone3();
    Integer getHrZone4();
    Integer getHrZone5();
    Integer getReps();
    Integer getCalories();
}
