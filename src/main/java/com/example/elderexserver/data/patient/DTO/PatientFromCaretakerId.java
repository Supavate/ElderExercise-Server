package com.example.elderexserver.data.patient.DTO;

public class PatientFromCaretakerId {
    private Integer id;
    private String picture;
    private String firstName;
    private String lastName;
    private String gender;
    private Integer age;

    public PatientFromCaretakerId(Integer id, String picture, String firstName, String lastName, String gender, Integer age) {
        this.id = id;
        this.picture = picture;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
