package com.example.elderexserver;

import jakarta.persistence.*;

@Entity
public class Patient_Allergy {
    @EmbeddedId
    Patient_AllergyKey id;

    @ManyToOne
    @MapsId("patient_id")
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @MapsId("allergy_id")
    @JoinColumn(name = "allergy_id")
    private Allergy allergy;

    public Patient_AllergyKey getId() {
        return id;
    }

    public void setId(Patient_AllergyKey id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Allergy getAllergy() {
        return allergy;
    }

    public void setAllergy(Allergy allergy) {
        this.allergy = allergy;
    }
}
