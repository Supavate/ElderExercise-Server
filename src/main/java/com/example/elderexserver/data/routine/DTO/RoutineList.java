package com.example.elderexserver.data.routine.DTO;

import java.util.Set;

public class RoutineList {
    public Integer id;
    public String name;
    public String description;
    public String staffFirst_name;
    public String staffLast_name;
    public Set<Exercise> exercise;

    public RoutineList(Integer id, String name, String description, String staffFirst_name, String staffLast_name, Set<Exercise> exercise) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.staffFirst_name = staffFirst_name;
        this.staffLast_name = staffLast_name;
        this.exercise = exercise;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStaffFirst_name() {
        return staffFirst_name;
    }

    public void setStaffFirst_name(String staffFirst_name) {
        this.staffFirst_name = staffFirst_name;
    }

    public String getStaffLast_name() {
        return staffLast_name;
    }

    public void setStaffLast_name(String staffLast_name) {
        this.staffLast_name = staffLast_name;
    }

    public Set<Exercise> getExercise() {
        return exercise;
    }

    public void setExercise(Set<Exercise> exercise) {
        this.exercise = exercise;
    }
}
