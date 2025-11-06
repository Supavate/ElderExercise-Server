package com.example.elderexserver.data.webSocket;

import com.example.elderexserver.data.exercise.Exercise_Session;
import com.example.elderexserver.data.exercise.Exercise_Session_Detail;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ConcurrentHashMap;

@Setter
@Getter
public class OngoingSession {
    private ConcurrentHashMap<Integer, Exercise> exercises;
    private Exercise_Session session;

    public void addSessionDetail(Exercise_Session_Detail detail) {
        this.session.addDetail(detail);
    }

    public void incrementExerciseCount(Integer exerciseId, String exerciseName) {
        this.exercises.compute(exerciseId, (id, currentExercise) -> {
            if (currentExercise == null) {
                return new Exercise(exerciseName);
            } else {
                currentExercise.setCount(currentExercise.getCount() + 1);
                return currentExercise;
            }
        });
    }

    @Getter
    @Setter
    public static class Exercise {
        private Integer count = 1;
        private String name;

        Exercise(String name) {
            this.name = name;
        }
    }
}
