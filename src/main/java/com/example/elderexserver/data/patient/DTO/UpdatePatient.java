package com.example.elderexserver.data.patient.DTO;

import lombok.Getter;

import java.util.Set;

@Getter
public class UpdatePatient {
    private Integer id;
    private String citizenId;
    private String firstName;
    private String lastName;
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
