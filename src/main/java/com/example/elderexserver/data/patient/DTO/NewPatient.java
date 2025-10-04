package com.example.elderexserver.data.patient.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class NewPatient {
    private String citizenId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Integer genderId;
    private Integer bloodTypeId;
    private Integer weight;
    private Integer height;
    private String dateOfBirth;
    private Integer nationalityId;
    private String phone;
    private String picture;
    private Integer caretakerId;
    private String note;
    private String surgicalHistory;
    private Integer primaryHospitalId;
    private String address;
    private Integer amphoeId;
    private Integer districtId;
    private Integer provinceId;
    private Set<Integer> foodAllergy;
    private Set<Integer> drugAllergy;
    private Set<Integer> medicine;
    private Integer statusId;
}
