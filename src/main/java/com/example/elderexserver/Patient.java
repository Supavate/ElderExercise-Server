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

    @ManyToOne
    @JoinColumn(name = "caretaker_id")
    private Staff staff;
    private String note;
    private Integer age;

    @ManyToMany
    @JoinTable(
            name = "Patient_Allergy",
            joinColumns = @JoinColumn(name = "Patient_id"),
            inverseJoinColumns = @JoinColumn(name = "Allergy_id"))
    private Set<Allergy> allergies;

    @OneToMany(mappedBy = "patient")
    private Set<Patient_Allergy> allergies;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_Name() {
        return first_Name;
    }

    public void setFirst_Name(String firstName) {
        this.first_Name = firstName;
    }

    public String getLast_Name() {
        return last_Name;
    }

    public void setLast_Name(String lastName) {
        this.last_Name = lastName;
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

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
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

    public Set<Allergy> getAllergies() {
        return allergies;
    }

    public void setAllergies(Set<Allergy> allergies) {
        this.allergies = allergies;
    }
}
