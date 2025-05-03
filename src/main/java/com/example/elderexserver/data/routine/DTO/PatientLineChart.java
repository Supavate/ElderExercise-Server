package com.example.elderexserver.data.routine.DTO;

import java.util.Set;

public class PatientLineChart {
    private Set<Exercise> exerciseSet;

    public PatientLineChart(Set<Exercise> exerciseSet) {
        this.exerciseSet = exerciseSet;
    }

    public Set<Exercise> getExerciseSet() {
        return exerciseSet;
    }

    public void setExerciseSet(Set<Exercise> exerciseSet) {
        this.exerciseSet = exerciseSet;
    }

    public static class Exercise {
        private Integer id;
        private String name;
        private Set<Day> daySet;

        public Exercise(Integer id, String name, Set<Day> daySet) {
            this.id = id;
            this.name = name;
            this.daySet = daySet;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Set<Day> getDaySet() {
            return daySet;
        }

        public void setDaySet(Set<Day> daySet) {
            this.daySet = daySet;
        }
    }

    public static class Day {
        private String dayName;
        private Integer reps;
        private Integer goals;

        public Day(String dayName, Integer reps, Integer goals) {
            this.dayName = dayName;
            this.reps = reps;
            this.goals = goals;
        }

        public String getDayName() {
            return dayName;
        }

        public void setDayName(String dayName) {
            this.dayName = dayName;
        }

        public Integer getReps() {
            return reps;
        }

        public void setReps(Integer reps) {
            this.reps = reps;
        }

        public Integer getGoals() {
            return goals;
        }

        public void setGoals(Integer goals) {
            this.goals = goals;
        }
    }
}
