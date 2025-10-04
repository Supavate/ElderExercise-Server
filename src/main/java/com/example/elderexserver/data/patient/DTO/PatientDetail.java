package com.example.elderexserver.data.patient.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
public class PatientDetail {
    private Integer id;
    private String citizen_id;
    private String first_name;
    private String last_name;
    private String email;
    private Integer gender_id;
    private String gender;
    private Integer blood_type_id;
    private String blood_type;
    private Integer weight;
    private Integer height;
    private Float bmi;
    private String date_of_birth;
    private Integer age;
    private Integer nationality_id;
    private String nationality;
    private String phone;
    private String picture;
    private String note;
    private String surgicalHistory;
    private Integer primary_hospital_id;
    private String primaryHospital;
    private String address;
    private Integer province_id;
    private String province;
    private Integer amphoe_id;
    private String amphoe;
    private Integer district_id;
    private String district;
    private String zipcode;
    private Set<Allergy> food_allergies;
    private Set<Allergy> drug_allergies;
    private Set<Medicine> medicines;


    @Setter
    @Getter
    @AllArgsConstructor
    public static class Allergy{
        private Integer id;
        private String name;
        private String description;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class Medicine{
        private Integer id;
        private String name;
        private String description;
    }

}
