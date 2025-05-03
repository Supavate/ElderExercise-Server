package com.example.elderexserver.data.routine.DTO;

import java.util.List;

public class PatientLineChart {
    private String week;
    private List<Exercise> exerciseList;

    public PatientLineChart(String week, List<Exercise> exerciseList) {
        this.week = week;
        this.exerciseList = exerciseList;
    }

    public static class Exercise {
        private Integer exerciseId;
        private String exerciseName;
        private List<Day> days;

        public Exercise(Integer exerciseId, String exerciseName, List<Day> days) {
            this.exerciseId = exerciseId;
            this.exerciseName = exerciseName;
            this.days = days;
        }

        public Integer getExerciseId() {
            return exerciseId;
        }

        public void setExerciseId(Integer exerciseId) {
            this.exerciseId = exerciseId;
        }

        public String getExerciseName() {
            return exerciseName;
        }

        public void setExerciseName(String exerciseName) {
            this.exerciseName = exerciseName;
        }

        public List<Day> getDays() {
            return days;
        }

        public void setDays(List<Day> days) {
            this.days = days;
        }
    }

    public static class Day {
        private String day;
        private Integer goal;
        private Integer done;

        public Day(String day, Integer goal, Integer done) {
            this.day = day;
            this.goal = goal;
            this.done = done;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public Integer getGoal() {
            return goal;
        }

        public void setGoal(Integer goal) {
            this.goal = goal;
        }

        public Integer getDone() {
            return done;
        }

        public void setDone(Integer done) {
            this.done = done;
        }
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }
}
