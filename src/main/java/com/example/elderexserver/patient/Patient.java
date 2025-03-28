package com.example.elderexserver.patient;

import com.example.elderexserver.staff.Staff;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Set;

@Entity
public class Patient {
    @Id
    private Integer id;
    private String first_Name;
    private String last_Name;
    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "blood_type_id")
    private Blood_Type blood_type;
    private Integer weight;
    private Integer height;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date_of_birth;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "caretaker_id")
    private Staff staff;
    private String note;

    @Transient
    private Integer age;

    @ManyToMany
    @JoinTable(
            name = "Patient_Allergy",
            joinColumns = @JoinColumn(name = "Patient_id"),
            inverseJoinColumns = @JoinColumn(name = "Allergy_id"))
    private Set<Allergy> allergies;

    @OneToMany(mappedBy = "patient")
    @JsonManagedReference
    private List<Patient_Note> notes;

    @OneToMany(mappedBy = "patient")
    @JsonManagedReference
    private List<Patient_Status> status;

    @OneToMany(mappedBy = "patient")
    @JsonManagedReference
    private List<Patient_Caretaker> caretakers;

    @OneToMany(mappedBy = "patient")
    @JsonManagedReference
    private List<Patient_Routine> routines;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_Name() {
        return first_Name;
    }

    public void setFirst_Name(String firstName) {
        this.first_Name = firstName;
    }

    public String getLast_Name() {
        return last_Name;
    }

    public void setLast_Name(String lastName) {
        this.last_Name = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Blood_Type getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(Blood_Type blood_type) {
        this.blood_type = blood_type;
    }

    public List<Patient_Status> getStatus() {
        return status;
    }

    public void setStatus(List<Patient_Status> status) {
        this.status = status;
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

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getAge() {
        return Period.between(this.date_of_birth, LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Allergy> getAllergies() {
        return allergies;
    }

    public void setAllergies(Set<Allergy> allergies) {
        this.allergies = allergies;
    }

    public List<Patient_Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Patient_Note> notes) {
        this.notes = notes;
    }

    public List<Patient_Caretaker> getCaretakers() {
        return caretakers;
    }

    public void setCaretakers(List<Patient_Caretaker> caretakers) {
        this.caretakers = caretakers;
    }

    public List<Patient_Routine> getRoutines() {
        return routines;
    }

    public void setRoutines(List<Patient_Routine> routines) {
        this.routines = routines;
    }
}