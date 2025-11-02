package com.example.elderexserver.data.routine;

import com.example.elderexserver.data.staff.Staff;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    @ManyToOne
    private Staff staff;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Routine_exercises> Exercises;

    public Routine(String name, String description, Staff staff) {
        this.name = name;
        this.description = description;
        this.staff = staff;
        Exercises = new HashSet<>();
    }
}
