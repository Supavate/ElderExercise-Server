package com.example.elderexserver.data.patient.DTO;

import java.util.Set;

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

    public String getCitizenId() {
        return citizenId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public Integer getBloodTypeId() {
        return bloodTypeId;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getHeight() {
        return height;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Integer getNationalityId() {
        return nationalityId;
    }

    public String getPhone() {
        return phone;
    }

    public String getPicture() {
        return picture;
    }

    public Integer getCaretakerId() {
        return caretakerId;
    }

    public String getNote() {
        return note;
    }

    public String getSurgicalHistory() {
        return surgicalHistory;
    }

    public Integer getPrimaryHospitalId() {
        return primaryHospitalId;
    }

    public String getAddress() {
        return address;
    }

    public Integer getAmphoeId() {
        return amphoeId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public Set<Integer> getFoodAllergy() {
        return foodAllergy;
    }

    public Set<Integer> getDrugAllergy() {
        return drugAllergy;
    }

    public Set<Integer> getMedicine() {
        return medicine;
    }

    public Integer getStatusId() {
        return statusId;
    }
}
