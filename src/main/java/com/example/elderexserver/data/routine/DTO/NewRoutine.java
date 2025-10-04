package com.example.elderexserver.data.routine.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class NewRoutine {
    private Integer staff_id;
    private String name;
    private String description;
    private List<routine_exercise> routine_exercises;

    @Setter
    @Getter
    @AllArgsConstructor
    public static class routine_exercise {
        private Integer exercise_id;
        private Integer rep;
        private Integer set;
        private Integer day;
    }

}
