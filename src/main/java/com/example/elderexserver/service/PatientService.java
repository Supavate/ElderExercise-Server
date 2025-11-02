package com.example.elderexserver.service;

import com.example.elderexserver.Exception.ResourceNotFoundException;
import com.example.elderexserver.data.address.Address;
import com.example.elderexserver.data.address.Amphoe;
import com.example.elderexserver.data.address.District;
import com.example.elderexserver.data.address.Province;
import com.example.elderexserver.data.patient.*;
import com.example.elderexserver.data.patient.DTO.*;
import com.example.elderexserver.data.staff.Staff;
import com.example.elderexserver.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;
    private final GenderRepository genderRepository;
    private final BloodTypeRepository bloodTypeRepository;
    private final FoodAllergyRepository foodAllergyRepository;
    private final DrugAllergyRepository drugAllergyRepository;
    private final AmphoeRepository amphoeRepository;
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;
    private final AddressRepository addressRepository;
    private final StaffRepository staffRepository;
    private final NationalityRepository nationalityRepository;
    private final HospitalRepository hospitalRepository;
    private final MedicineRepository medicineRepository;
    private final StatusRepository statusRepository;

    public List<PatientListView> getPatientList() {
        return patientRepository.findPatientList();
    }

    public List<PatientFromCaretakerIdView> getPatientFromCaretakerId(Integer id) {
        return patientRepository.findAllByCaretakerId(id);
    }

    public List<PatientWithAge> getPatientsWithAge() {
        return patientRepository.findAllWithAge().stream()
                .map(p -> new PatientWithAge(
                        p.getId(),
                        p.getFirstName(),
                        p.getLastName(),
                        p.getWeight(),
                        p.getHeight(),
                        p.getAge()
                ))
                .toList();
    }

    public PatientDetail getPatientDetailById(int id) {
        List<PatientDetailView> patientDetailViews = patientRepository.findPatientDetailById(id);
        if (patientDetailViews.isEmpty()) {
            throw new ResourceNotFoundException("Patient not found with ID: " + id);
        }

        Set<PatientDetail.Allergy> foodAllergies = new HashSet<>();
        Set<PatientDetail.Allergy> drugAllergies = new HashSet<>();
        Set<PatientDetail.Medicine> medicines = new HashSet<>();

        for (PatientDetailView row : patientDetailViews) {
            foodAllergies.add(new PatientDetail.Allergy(row.getFoodId(), row.getFoodName(), row.getFoodDescription()));
            drugAllergies.add(new PatientDetail.Allergy(row.getDrugId(), row.getDrugName(), row.getDrugDescription()));
            medicines.add(new PatientDetail.Medicine(row.getMedicineId(), row.getMedicineName(), row.getMedicineDescription()));
        }

        PatientDetailView patient = patientDetailViews.get(0);

        return new PatientDetail(
                patient.getId(),
                patient.getCitizenId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getEmail(),
                patient.getGenderId(),
                patient.getGender(),
                patient.getBloodTypeId(),
                patient.getBloodType(),
                patient.getWeight(),
                patient.getHeight(),
                patient.getBmi(),
                patient.getDateOfBirth(),
                patient.getAge(),
                patient.getNationalityId(),
                patient.getNationality(),
                patient.getPhone(),
                patient.getPicture(),
                patient.getNote(),
                patient.getSurgicalHistory(),
                patient.getPrimaryHospitalId(),
                patient.getPrimaryHospital(),
                patient.getAddress(),
                patient.getProvinceId(),
                patient.getProvince(),
                patient.getAmphoeId(),
                patient.getAmphoe(),
                patient.getDistrictId(),
                patient.getDistrict(),
                patient.getZipcode(),
                foodAllergies,
                drugAllergies,
                medicines
        );
    }

    @Transactional
    public Patient newPatient(NewPatient newPatient) {
        Province province = provinceRepository.findById(newPatient.getProvinceId())
                .orElseThrow(() -> new ResourceNotFoundException("Province not found"));

        Amphoe amphoe = amphoeRepository.findById(newPatient.getAmphoeId())
                .orElseThrow(() -> new ResourceNotFoundException("Amphoe not found"));

        District district = districtRepository.findById(newPatient.getDistrictId())
                .orElseThrow(() -> new ResourceNotFoundException("District not found"));

        Address address = new Address(newPatient.getAddress(), district);
        addressRepository.save(address);

        Gender gender = genderRepository.findById(newPatient.getGenderId())
                .orElseThrow(() -> new ResourceNotFoundException("Gender not found"));

        Blood_Type bloodType = bloodTypeRepository.findById(newPatient.getBloodTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Blood type not found"));

        Staff caretaker = staffRepository.findById(newPatient.getCaretakerId())
                .orElseThrow(() -> new ResourceNotFoundException("Caretaker not found"));

        Nationality nationality = nationalityRepository.findById(newPatient.getNationalityId())
                .orElseThrow(() -> new ResourceNotFoundException("Nationality not found"));

        Hospital hospital = hospitalRepository.findById(newPatient.getPrimaryHospitalId())
                .orElseThrow(() -> new ResourceNotFoundException("Primary hospital not found"));

        List<Food_Allergy> foodAllergies = foodAllergyRepository.findAllById(newPatient.getFoodAllergy());
        List<Drug_Allergy> drugAllergies = drugAllergyRepository.findAllById(newPatient.getDrugAllergy());
        List<Medicine> medicines = medicineRepository.findAllById(newPatient.getMedicine());

        Status status = statusRepository.findById(newPatient.getStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("Status not found"));

        String hashedPassword = passwordEncoder.encode(newPatient.getPassword());

        Patient patient = new Patient(
                newPatient.getCitizenId(),
                newPatient.getFirstName(),
                newPatient.getLastName(),
                newPatient.getEmail(),
                hashedPassword,
                gender,
                bloodType,
                newPatient.getWeight(),
                newPatient.getHeight(),
                LocalDate.parse(newPatient.getDateOfBirth(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                nationality,
                newPatient.getPhone(),
                newPatient.getPicture(),
                caretaker,
                newPatient.getNote(),
                newPatient.getSurgicalHistory(),
                hospital,
                address,
                new HashSet<>(drugAllergies),
                new HashSet<>(foodAllergies),
                new HashSet<>(medicines),
                new HashSet<>()
        );

        patient.addPatientStatus(new Patient_Status(status, LocalDate.now()));

        return patientRepository.save(patient);
    }

    @Transactional
    public Patient updatePatient(UpdatePatient updatePatient) {
        Patient patient = patientRepository.findById(updatePatient.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Optional.ofNullable(updatePatient.getCitizenId()).ifPresent(patient::setCitizenId);
        Optional.ofNullable(updatePatient.getFirstName()).ifPresent(patient::setFirstName);
        Optional.ofNullable(updatePatient.getLastName()).ifPresent(patient::setLastName);
        Optional.ofNullable(updatePatient.getWeight()).ifPresent(patient::setWeight);
        Optional.ofNullable(updatePatient.getHeight()).ifPresent(patient::setHeight);
        Optional.ofNullable(updatePatient.getPhone()).ifPresent(patient::setPhone);
        Optional.ofNullable(updatePatient.getPicture()).ifPresent(patient::setPicture);
        Optional.ofNullable(updatePatient.getNote()).ifPresent(patient::setNote);
        Optional.ofNullable(updatePatient.getSurgicalHistory()).ifPresent(patient::setSurgicalHistory);
        Optional.ofNullable(updatePatient.getAddress()).ifPresent(patient.getAddress()::setAddress);

        if (updatePatient.getGenderId() != null) {
            patient.setGender(genderRepository.findById(updatePatient.getGenderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Gender not found")));
        }

        if (updatePatient.getBloodTypeId() != null) {
            patient.setBloodType(bloodTypeRepository.findById(updatePatient.getBloodTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Blood type not found")));
        }

        if (updatePatient.getDistrictId() != null) {
            District district = districtRepository.findById(updatePatient.getDistrictId())
                    .orElseThrow(() -> new ResourceNotFoundException("District not found"));
            patient.getAddress().setDistrict(district);
        }

        if (updatePatient.getNationalityId() != null) {
            patient.setNationality(nationalityRepository.findById(updatePatient.getNationalityId())
                    .orElseThrow(() -> new ResourceNotFoundException("Nationality not found")));
        }

        if (updatePatient.getCaretakerId() != null) {
            patient.setCaretaker(staffRepository.findById(updatePatient.getCaretakerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Caretaker not found")));
        }

        if (updatePatient.getPrimaryHospitalId() != null) {
            patient.setPrimaryHospital(hospitalRepository.findById(updatePatient.getPrimaryHospitalId())
                    .orElseThrow(() -> new ResourceNotFoundException("Primary hospital not found")));
        }

        if (updatePatient.getFoodAllergy() != null) {
            patient.setFoodAllergies(new HashSet<>(foodAllergyRepository.findAllById(updatePatient.getFoodAllergy())));
        }

        if (updatePatient.getDrugAllergy() != null) {
            patient.setDrugAllergies(new HashSet<>(drugAllergyRepository.findAllById(updatePatient.getDrugAllergy())));
        }

        if (updatePatient.getMedicine() != null) {
            patient.setMedicines(new HashSet<>(medicineRepository.findAllById(updatePatient.getMedicine())));
        }

        if (updatePatient.getStatusId() != null) {
            Status status = statusRepository.findById(updatePatient.getStatusId())
                    .orElseThrow(() -> new ResourceNotFoundException("Status not found"));
            patient.addPatientStatus(new Patient_Status(status, LocalDate.now()));
        }

        if (updatePatient.getDateOfBirth() != null) {
            try {
                patient.setDateOfBirth(LocalDate.parse(updatePatient.getDateOfBirth(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format. Please use dd/MM/yyyy");
            }
        }

        return patientRepository.save(patient);
    }

    public Patient findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }
}
