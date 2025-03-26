package com.example.elderexserver;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RoutinesExercisesKey implements Serializable {
    private int routine_id;
    private int exercise_id;
}
