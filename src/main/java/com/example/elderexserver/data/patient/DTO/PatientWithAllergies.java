package com.example.elderexserver.data.patient.DTO;

import java.util.HashSet;
import java.util.Set;

public class PatientWithAllergies {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer weight;
    private Integer height;
    private Set<String> allergy;

    public PatientWithAllergies(Integer id, String firstName, String lastName, Integer weight, Integer height, Set<String> allergy) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.weight = weight;
        this.height = height;
        this.allergy = allergy;
    }

    public void addAllergy(String allergy) {
        if (allergy != null) this.allergy.add(allergy);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Set<String> getAllergy() {
        return allergy;
    }

    public void setAllergy(Set<String> allergy) {
        this.allergy = allergy;
    }
}
