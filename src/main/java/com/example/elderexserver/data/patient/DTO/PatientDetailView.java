package com.example.elderexserver.data.patient.DTO;

public interface PatientDetailView {
    Integer getId();
    String getPicture();
    String getCitizenId();
    String getFirstName();
    String getLastName();
    Integer getGenderId();
    String getGender();
    String getDateOfBirth();
    Integer getAge();
    Integer getBloodTypeId();
    String getBloodType();
    Integer getWeight();
    Integer getHeight();
    Integer getAllergyId();
    String getAllergyName();
    String getAllergyDescription();
    String getPhone();
    String getAddress();
    String getProvince();
    String getAmphoe();
    String getDistrict();
    String getZipcode();
    String getNote();
}
