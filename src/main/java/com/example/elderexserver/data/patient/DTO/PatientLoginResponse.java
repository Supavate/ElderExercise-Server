package com.example.elderexserver.data.patient.DTO;

public class PatientLoginResponse {
    private String token;
    private int id;
    private String CitizenId;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String picture;
    private String gender;
    private String bloodType;
    private String caretakerName;
    private String message;
    private Long expiresIn;

    public PatientLoginResponse(String token, int id, String citizenId, String email, String firstName, String lastName, String phone, String picture, String gender, String bloodType, String caretakerName, String message) {
        this.token = token;
        this.id = id;
        CitizenId = citizenId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.picture = picture;
        this.gender = gender;
        this.bloodType = bloodType;
        this.caretakerName = caretakerName;
        this.message = message;
        this.expiresIn = 86400L; // 24 hours in seconds
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCitizenId() {
        return CitizenId;
    }

    public void setCitizenId(String citizenId) {
        CitizenId = citizenId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getCaretakerName() {
        return caretakerName;
    }

    public void setCaretakerName(String caretakerName) {
        this.caretakerName = caretakerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
