package com.example.elderexserver;

import jakarta.persistence.Embeddable;

@Embeddable
public class Patient_AllergyKey {
    private int patient_id;
    private int allergy_id;
}
