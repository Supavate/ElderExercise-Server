package com.example.elderexserver.data.exercise.DTO;

import com.example.elderexserver.data.exercise.Exercise_Session;
import com.example.elderexserver.data.exercise.Exercise_Session_Detail;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ConcurrentHashMap;

@Setter
@Getter
public class OngoingSession {
    private ConcurrentHashMap<Integer, Integer> count;
    private Exercise_Session session;

    public void addSessionDetail(Exercise_Session_Detail detail) {
        this.session.addDetail(detail);
    }

    public void incrementExerciseCount(Integer exerciseId) {
        this.count.merge(exerciseId, 1, Integer::sum);
    }
}
