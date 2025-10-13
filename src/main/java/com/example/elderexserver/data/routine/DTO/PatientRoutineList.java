package com.example.elderexserver.data.routine.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
public class PatientRoutineList {
    private String routineName;
    private String routineDescription;
    private Integer patientRoutineId;
    private Set<Exercise> exercises;

    @Setter
    @Getter
    @AllArgsConstructor
    public static class Exercise {
        public Integer exerciseId;
        public String name;
        public Integer rep;
        public Integer set;
        public Integer day;
    }

}
