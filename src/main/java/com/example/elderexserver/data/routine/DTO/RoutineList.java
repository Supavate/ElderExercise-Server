package com.example.elderexserver.data.routine.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
public class RoutineList {
    public Integer id;
    public String name;
    public String description;
    public String staffFirst_name;
    public String staffLast_name;
    public Set<Exercise> exercise;

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
