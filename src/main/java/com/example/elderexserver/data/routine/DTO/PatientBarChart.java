package com.example.elderexserver.data.routine.DTO;

import java.util.List;

public class PatientBarChart {
    private String week;
    private String routineName;
    private String routineDescription;
    private List<exercise> exerciseList;

    public PatientBarChart(String week, String routineName, String routineDescription, List<exercise> exerciseList) {
        this.routineName = routineName;
        this.routineDescription = routineDescription;
        this.week = week;
        this.exerciseList = exerciseList;
    }

    public static class exercise {
        private String exercise;
        private int done;
        private int missing;

        public exercise(String exercise, int done, int missing) {
            this.exercise = exercise;
            this.done = done;
            this.missing = missing;
        }

        public String getExercise() {
            return exercise;
        }

        public void setExercise(String exercise) {
            this.exercise = exercise;
        }

        public int getDone() {
            return done;
        }

        public void setDone(int done) {
            this.done = done;
        }

        public int getMissing() {
            return missing;
        }

        public void setMissing(int missing) {
            this.missing = missing;
        }
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
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

    public List<exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }
}
