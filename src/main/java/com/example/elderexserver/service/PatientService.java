package com.example.elderexserver.service;

import com.example.elderexserver.data.address.Address;
import com.example.elderexserver.data.address.Amphoe;
import com.example.elderexserver.data.address.District;
import com.example.elderexserver.data.address.Province;
import com.example.elderexserver.data.patient.Allergy;
import com.example.elderexserver.data.patient.Blood_Type;
import com.example.elderexserver.data.patient.DTO.*;
import com.example.elderexserver.data.patient.Gender;
import com.example.elderexserver.data.patient.Patient;
import com.example.elderexserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private BloodTypeRepository bloodTypeRepository;

    @Autowired
    private AllergyRepository allergyRepository;

    @Autowired
    private AmphoeRepository amphoeRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    public List<PatientListView> getPatientList() {
        return patientRepository.findPatientList();
    }

    public List<PatientFromCaretakerIdView> getPatientFromCaretakerId(Integer id) {
        return patientRepository.findAllByCaretakerId(id);
    }

    public List<PatientWithAge> getPatientsWithAge() {
        List<PatientWithAgeView> patientsWithAge = patientRepository.findAllWithAge();

        return patientsWithAge.stream()
                .map(p -> new PatientWithAge(
                        p.getId(),
                        p.getFirstName(),
                        p.getLastName(),
                        p.getWeight(),
                        p.getHeight(),
                        p.getAge()))
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
        List<PatientDetailView> patientDetailViews = patientRepository.findPatientDetailById(id);
        Set<PatientDetail.Allergy> allergies = new HashSet<>();
        for (PatientDetailView row : patientDetailViews) {
            allergies.add(new PatientDetail.Allergy(
                    row.getAllergyId(),
                    row.getAllergyName(),
                    row.getAllergyDescription()
                    )
            );
        }

        PatientDetailView patient = patientDetailViews.get(0);
        return new PatientDetail(
                patient.getId(),
                patient.getPicture(),
                patient.getCitizenId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getGenderId(),
                patient.getGender(),
                patient.getDateOfBirth(),
                patient.getAge(),
                patient.getBloodTypeId(),
                patient.getBloodType(),
                patient.getWeight(),
                patient.getHeight(),
                patient.getPhone(),
                patient.getAddress(),
                patient.getProvince(),
                patient.getAmphoe(),
                patient.getDistrict(),
                patient.getZipcode(),
                allergies,
                patient.getNote()
        );
    }

    public Patient newPatient(NewPatient newPatient) {

        Province province = provinceRepository.findById(newPatient.getProvinceId())
                .orElseThrow(() -> new IllegalArgumentException("Province not found"));

        Amphoe amphoe = amphoeRepository.findById(newPatient.getAmphoeId())
                .orElseThrow(() -> new IllegalArgumentException("Amphoe not found"));

        District district = districtRepository.findById(newPatient.getDistrictId())
                .orElseThrow(() -> new IllegalArgumentException("District not found"));

        Address address = new Address(newPatient.getAddress(), district);

        Gender gender = genderRepository.findById(newPatient.getGenderId())
                .orElseThrow(() -> new IllegalArgumentException("Gender not found"));

        Blood_Type bloodType = bloodTypeRepository.findById(newPatient.getBloodTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Blood type not found"));

        List<Allergy> allergy = allergyRepository.findAllById(newPatient.getAllergy());

        Patient patient = new Patient(
                newPatient.getCitizenId(),
                newPatient.getFirstName(),
                newPatient.getLastName(),
                gender,
                bloodType,
                newPatient.getWeight(),
                newPatient.getHeight(),
                LocalDate.parse(newPatient.getDateOfBirth(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                newPatient.getPhone(),
                newPatient.getNote(),
                address,
                new HashSet<>(allergy)
        );

        return patientRepository.save(patient);
    }
}
