package com.example.elderexserver.data.patient.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

}
