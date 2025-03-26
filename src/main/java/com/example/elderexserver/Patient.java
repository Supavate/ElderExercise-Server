package com.example.elderexserver;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
public class Patient {
    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private String gender;
    private String blood_type;
    private Integer weight;
    private Integer height;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date_of_birth;
    private String phone;
    private Integer caretaker;
    private String note;
    private Integer age;
    @OneToMany(mappedBy = "patient")
    private Set<Patient_Allergy> allergies;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCaretaker() {
        return caretaker;
    }

    public void setCaretaker(Integer caretaker) {
        this.caretaker = caretaker;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Patient_Allergy> getAllergies() {
        return allergies;
    }

    public void setAllergies(Set<Patient_Allergy> allergies) {
        this.allergies = allergies;
    }
}
