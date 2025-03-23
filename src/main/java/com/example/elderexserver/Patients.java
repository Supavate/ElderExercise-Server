package com.example.elderexserver;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Patients {
    @Id
    private Integer Patient_ID;
    private String First_name;
    private String Last_name;
    private String Gender;
    private String Blood_type;
    private Integer Weight;
    private Integer Height;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;
    private Integer Main_Caretaker;
    private Integer Status;
    private String Special_Note;

    public Integer getPatient_ID() {
        return Patient_ID;
    }

    public void setPatient_ID(Integer patient_ID) {
        Patient_ID = patient_ID;
    }

    public String getFirst_name() {
        return First_name;
    }

    public void setFirst_name(String first_name) {
        First_name = first_name;
    }

    public String getLast_name() {
        return Last_name;
    }

    public void setLast_name(String last_name) {
        Last_name = last_name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBlood_type() {
        return Blood_type;
    }

    public void setBlood_type(String blood_type) {
        Blood_type = blood_type;
    }

    public Integer getWeight() {
        return Weight;
    }

    public void setWeight(Integer weight) {
        Weight = weight;
    }

    public Integer getHeight() {
        return Height;
    }

    public void setHeight(Integer height) {
        Height = height;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getMain_Caretaker() {
        return Main_Caretaker;
    }

    public void setMain_Caretaker(Integer main_Caretaker) {
        Main_Caretaker = main_Caretaker;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getSpecial_Note() {
        return Special_Note;
    }

    public void setSpecial_Note(String special_Note) {
        Special_Note = special_Note;
    }
}
