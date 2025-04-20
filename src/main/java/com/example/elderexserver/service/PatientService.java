package com.example.elderexserver.service;

import com.example.elderexserver.data.patient.DTO.PatientWithAge;
import com.example.elderexserver.data.patient.DTO.PatientWithAllergies;
import com.example.elderexserver.data.patient.DTO.PatientWithAgeView;
import com.example.elderexserver.data.patient.DTO.PatientWithAllergiesView;
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
}
