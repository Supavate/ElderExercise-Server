package com.example.elderexserver.data.staff;

import com.example.elderexserver.data.patient.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String first_Name;
    private String last_Name;
    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;
    private String email;
    private String telephone;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date_of_birth;
    private String username;
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private String picture;
    private Integer supervisor_id;

    public Staff(String first_Name, String last_Name, Gender gender, String email, LocalDate date_of_birth, String username, String password, Role role, String picture, String telephone) {
        this.first_Name = first_Name;
        this.last_Name = last_Name;
        this.gender = gender;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.username = username;
        this.password = password;
        this.role = role;
        this.picture = picture;
        this.telephone = telephone;
    }
}
