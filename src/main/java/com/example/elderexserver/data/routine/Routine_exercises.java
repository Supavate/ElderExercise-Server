package com.example.elderexserver.data.routine;

import com.example.elderexserver.data.exercise.Exercise;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Routine_exercises {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "routine_id")
    @JsonBackReference
    private Routine routine;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    @JsonBackReference
    private Exercise exercise;

    private int rep;
    @Column(name = "`set`")
    private int set;
    private int day;

    public Routine_exercises(Routine routine, Exercise exercise, int rep, int set, int day) {
        this.routine = routine;
        this.exercise = exercise;
        this.rep = rep;
        this.set = set;
        this.day = day;
    }
}
