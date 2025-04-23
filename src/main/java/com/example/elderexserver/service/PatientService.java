package com.example.elderexserver.service;

import com.example.elderexserver.data.patient.DTO.*;
import com.example.elderexserver.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public List<PatientWithAge> getPatientsWithAge() {
        List<PatientWithAgeView> patientsWithAge = patientRepository.findAllWithAge();

        return patientsWithAge.stream()
                .map(p -> new PatientWithAge(p.getId(), p.getFirstName(), p.getLastName(), p.getWeight(), p.getHeight(), p.getAge()))
                .toList();
    }

    public List<PatientWithAllergies> getPatientsWithAllergies() {
        List<PatientWithAllergiesView> patientWithAllergies = patientRepository.findAllWithAllergies();

        return patientWithAllergies.stream()
                .map(p -> new PatientWithAllergies(p.getId(), p.getFirstName(), p.getLastName(), p.getWeight(), p.getHeight(),
                        new HashSet<>(Arrays.asList(
                                (p.getAllergies() != null) ? p.getAllergies().split(",") : new String[0])
                        )
                ))
                .toList();
    }

    public List<PatientFromCaretakerId> getPatientsByCaretakerId(int caretakerId) {
        List<PatientFromCaretakerIdView> patientFromCaretakerId = patientRepository.findAllByCaretakerId(caretakerId);
        return patientFromCaretakerId.stream()
                .map(p -> new PatientFromCaretakerId(p.getId(), p.getPicture(), p.getFirstName(), p.getLastName(), p.getGender(), p.getAge()))
                .toList();
    }

    public PatientDetail getPatientDetailById(int id) {
        PatientDetailView p = patientRepository.findPatientDetailById(id);
        return new PatientDetail(p.getId(), p.getPicture(), p.getCitizenId(), p.getFirstName(), p.getLastName(), p.getGenderId(), p.getGender(), p.getDateOfBirth(), p.getAge(), p.getBloodTypeId(), p.getBloodType(), p.getWeight(), p.getHeight(), (p.getAllergy() != null) ? new HashSet<>(Arrays.asList(p.getAllergy().split(","))) : new HashSet<>(), p.getPhone(), p.getAddress(), p.getProvince(), p.getAmphoe(), p.getDistrict(), p.getZipcode(), p.getNote());
    }
}
