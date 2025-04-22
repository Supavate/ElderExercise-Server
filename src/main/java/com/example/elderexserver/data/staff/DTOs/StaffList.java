package com.example.elderexserver.data.staff.DTOs;

public class StaffList {
    Integer id;
    String picture;
    String first_name;
    String last_name;
    Integer patientCount;

    public StaffList(Integer id, String picture, String first_name, String last_name, Integer patientCount) {
        this.id = id;
        this.picture = picture;
        this.first_name = first_name;
        this.last_name = last_name;
        this.patientCount = patientCount;
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(Integer patientCount) {
        this.patientCount = patientCount;
    }
}
