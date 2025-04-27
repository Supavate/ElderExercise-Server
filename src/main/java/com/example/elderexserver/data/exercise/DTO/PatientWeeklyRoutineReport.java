package com.example.elderexserver.data.exercise.DTO;

import java.util.Set;

public class PatientWeeklyRoutineReport {
    String id;
    Integer patient_id;
    String first_name;
    String last_name;
    Integer year;
    Integer week_number;
    String week_start_date;
    String week_end_date;
    Set<Exercise> exerciseSet;

    public PatientWeeklyRoutineReport(String id, Integer patient_id, String first_name, String last_name, Integer year, Integer week_number, String week_start_date, String week_end_date, Set<Exercise> exerciseSet) {
        this.id = id;
        this.patient_id = patient_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.year = year;
        this.week_number = week_number;
        this.week_start_date = week_start_date;
        this.week_end_date = week_end_date;
        this.exerciseSet = exerciseSet;
    }

    public static class Exercise {
        Integer exercise_id;
        String exercise_name;
        Integer total_reps;
        Integer weekly_rep_goal;
        Float percentage_done;

        public Exercise(Integer exercise_id, String exercise_name, Integer total_reps, Integer weekly_rep_goal, Float percentage_done) {
            this.exercise_id = exercise_id;
            this.exercise_name = exercise_name;
            this.total_reps = total_reps;
            this.weekly_rep_goal = weekly_rep_goal;
            this.percentage_done = percentage_done;
        }

        public Integer getExercise_id() {
            return exercise_id;
        }

        public void setExercise_id(Integer exercise_id) {
            this.exercise_id = exercise_id;
        }

        public String getExercise_name() {
            return exercise_name;
        }

        public void setExercise_name(String exercise_name) {
            this.exercise_name = exercise_name;
        }

        public Integer getTotal_reps() {
            return total_reps;
        }

        public void setTotal_reps(Integer total_reps) {
            this.total_reps = total_reps;
        }

        public Integer getWeekly_rep_goal() {
            return weekly_rep_goal;
        }

        public void setWeekly_rep_goal(Integer weekly_rep_goal) {
            this.weekly_rep_goal = weekly_rep_goal;
        }

        public Float getPercentage_done() {
            return percentage_done;
        }

        public void setPercentage_done(Float percentage_done) {
            this.percentage_done = percentage_done;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Integer patient_id) {
        this.patient_id = patient_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getWeek_number() {
        return week_number;
    }

    public void setWeek_number(Integer week_number) {
        this.week_number = week_number;
    }

    public String getWeek_start_date() {
        return week_start_date;
    }

    public void setWeek_start_date(String week_start_date) {
        this.week_start_date = week_start_date;
    }

    public String getWeek_end_date() {
        return week_end_date;
    }

    public void setWeek_end_date(String week_end_date) {
        this.week_end_date = week_end_date;
    }

    public Set<Exercise> getExerciseSet() {
        return exerciseSet;
    }

    public void setExerciseSet(Set<Exercise> exerciseSet) {
        this.exerciseSet = exerciseSet;
    }
}
