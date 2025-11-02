package com.example.elderexserver.data.routine.DTO;

import lombok.Getter;

import java.util.List;

@Getter
public class NewRoutine {
    private Integer staff_id;
    private String name;
    private String description;
    private List<routine_exercise> routine_exercises;
    private NewPatientRoutine patient_routine;

    @Getter
    public static class routine_exercise {
        private Integer exercise_id;
        private Integer rep;
        private Integer set;
        private Integer day;
    }
}
