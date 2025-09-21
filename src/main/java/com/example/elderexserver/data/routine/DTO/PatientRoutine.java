package com.example.elderexserver.data.routine.DTO;

import java.util.Set;

public class PatientRoutine {
    private String routineName;
    private String routineDescription;
    private Integer patientRoutineId;
    private Set<Exercise> exercises;

    public PatientRoutine(String routineName, String routineDescription, Integer patientRoutineId, Set<Exercise> exercises) {
        this.routineName = routineName;
        this.routineDescription = routineDescription;
        this.patientRoutineId = patientRoutineId;
        this.exercises = exercises;
    }

    public static class Exercise {
        public Integer exerciseId;
        public String name;
        public Integer rep;
        public Integer set;
        public Integer day;

        public Exercise(Integer exerciseId, String name, Integer rep, Integer set, Integer day) {
            this.exerciseId = exerciseId;
            this.name = name;
            this.rep = rep;
            this.set = set;
            this.day = day;
        }

        public Integer getExerciseId() {
            return exerciseId;
        }

        public void setExerciseId(Integer exerciseId) {
            this.exerciseId = exerciseId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getRep() {
            return rep;
        }

        public void setRep(Integer rep) {
            this.rep = rep;
        }

        public Integer getSet() {
            return set;
        }

        public void setSet(Integer set) {
            this.set = set;
        }

        public Integer getDay() {
            return day;
        }

        public void setDay(Integer day) {
            this.day = day;
        }
    }

    public String getRoutineName() {
        return routineName;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }

    public String getRoutineDescription() {
        return routineDescription;
    }

    public void setRoutineDescription(String routineDescription) {
        this.routineDescription = routineDescription;
    }

    public Integer getPatientRoutineId() {
        return patientRoutineId;
    }

    public void setPatientRoutineId(Integer patientRoutineId) {
        this.patientRoutineId = patientRoutineId;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }
}
