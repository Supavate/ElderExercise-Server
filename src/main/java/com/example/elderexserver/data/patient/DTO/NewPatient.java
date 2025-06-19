package com.example.elderexserver.data.patient.DTO;

import java.util.Set;

public class NewPatient {
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

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
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

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public Integer getBloodTypeId() {
        return bloodTypeId;
    }

    public void setBloodTypeId(Integer bloodTypeId) {
        this.bloodTypeId = bloodTypeId;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(Integer nationalityId) {
        this.nationalityId = nationalityId;
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

    public Integer getCaretakerId() {
        return caretakerId;
    }

    public void setCaretakerId(Integer caretakerId) {
        this.caretakerId = caretakerId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSurgicalHistory() {
        return surgicalHistory;
    }

    public void setSurgicalHistory(String surgicalHistory) {
        this.surgicalHistory = surgicalHistory;
    }

    public Integer getPrimaryHospitalId() {
        return primaryHospitalId;
    }

    public void setPrimaryHospitalId(Integer primaryHospitalId) {
        this.primaryHospitalId = primaryHospitalId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAmphoeId() {
        return amphoeId;
    }

    public void setAmphoeId(Integer amphoeId) {
        this.amphoeId = amphoeId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Set<Integer> getFoodAllergy() {
        return foodAllergy;
    }

    public void setFoodAllergy(Set<Integer> foodAllergy) {
        this.foodAllergy = foodAllergy;
    }

    public Set<Integer> getDrugAllergy() {
        return drugAllergy;
    }

    public void setDrugAllergy(Set<Integer> drugAllergy) {
        this.drugAllergy = drugAllergy;
    }

    public Set<Integer> getMedicine() {
        return medicine;
    }

    public void setMedicine(Set<Integer> medicine) {
        this.medicine = medicine;
    }
}
