package com.example.elderexserver.data.routine.DTO;


import java.time.LocalDate;
import java.util.Set;

public class PatientDailyRoutineReport {
    private Integer patientId;
    private String firstName;
    private String lastName;
    private LocalDate exerciseDate;
    private Set<Exercise> exerciseSet;

    public PatientDailyRoutineReport(Integer patientId, String firstName, String lastName, LocalDate exerciseDate, Set<Exercise> exerciseSet) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.exerciseDate = exerciseDate;
        this.exerciseSet = exerciseSet;
    }

    public static class Exercise{
        private Integer exerciseId;
        private String exerciseName;
        private Integer totalReps;
        private Integer repGoal;
        private Float percentageDone;

        public Exercise(Integer exerciseId, String exerciseName, Integer totalReps, Integer repGoal, Float percentageDone) {
            this.exerciseId = exerciseId;
            this.exerciseName = exerciseName;
            this.totalReps = totalReps;
            this.repGoal = repGoal;
            this.percentageDone = percentageDone;
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

        public Integer getTotalReps() {
            return totalReps;
        }

        public void setTotalReps(Integer totalReps) {
            this.totalReps = totalReps;
        }

        public Integer getRepGoal() {
            return repGoal;
        }

        public void setRepGoal(Integer repGoal) {
            this.repGoal = repGoal;
        }

        public Float getPercentageDone() {
            return percentageDone;
        }

        public void setPercentageDone(Float percentageDone) {
            this.percentageDone = percentageDone;
        }
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getExerciseDate() {
        return exerciseDate;
    }

    public void setExerciseDate(LocalDate exerciseDate) {
        this.exerciseDate = exerciseDate;
    }

    public Set<Exercise> getExerciseSet() {
        return exerciseSet;
    }

    public void setExerciseSet(Set<Exercise> exerciseSet) {
        this.exerciseSet = exerciseSet;
    }
}
