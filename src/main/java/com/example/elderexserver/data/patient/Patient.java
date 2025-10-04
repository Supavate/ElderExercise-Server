package com.example.elderexserver.data.patient;

import com.example.elderexserver.data.address.Address;
import com.example.elderexserver.data.routine.Patient_Routine;
import com.example.elderexserver.data.staff.Staff;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "citizen_id", nullable = false, length = 13)
    private String citizenId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gender_id", nullable = false)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blood_type_id")
    private Blood_Type bloodType;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "height")
    private Integer height;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nationality_id", nullable = false)
    private Nationality nationality;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "picture")
    private String picture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caretaker_id")
    private Staff caretaker;

    @Column(name = "note")
    private String note;

    @Column(name = "surgical_history", nullable = false)
    private String surgicalHistory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "primary_hospital_id", nullable = false)
    private Hospital primaryHospital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany
    @JoinTable(name = "patient_drug_allergy",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "drug_allergy_id"))
    private Set<Drug_Allergy> drugAllergies = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "patient_food_allergy",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "food_allergy_id"))
    private Set<Food_Allergy> foodAllergies = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "patient_medication",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id"))
    private Set<Medicine> medicines = new LinkedHashSet<>();

    @OneToMany(mappedBy = "patient")
    private Set<Patient_Note> patientNotes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "patient")
    private Set<Patient_Routine> patientRoutines = new LinkedHashSet<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Patient_Status> patientStatuses = new LinkedHashSet<>();

    public Patient() {
    }

    public Patient(String citizenId, String firstName, String lastName, String email, String password, Gender gender, Blood_Type bloodType, Integer weight, Integer height, LocalDate dateOfBirth, Nationality nationality, String phone, String picture, Staff caretaker, String note, String surgicalHistory, Hospital primaryHospital, Address address, Set<Drug_Allergy> drugAllergies, Set<Food_Allergy> foodAllergies, Set<Medicine> medicines, Set<Patient_Status> patientStatuses) {
        this.citizenId = citizenId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.bloodType = bloodType;
        this.weight = weight;
        this.height = height;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.phone = phone;
        this.picture = picture;
        this.caretaker = caretaker;
        this.note = note;
        this.surgicalHistory = surgicalHistory;
        this.primaryHospital = primaryHospital;
        this.address = address;
        this.drugAllergies = drugAllergies;
        this.foodAllergies = foodAllergies;
        this.medicines = medicines;
        this.patientStatuses = patientStatuses;
    }

    public void addPatientStatus(Patient_Status status) {
        status.setPatient(this);
        this.patientStatuses.add(status);
    }
}