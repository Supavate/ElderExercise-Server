package com.example.elderexserver.service;

import com.example.elderexserver.data.address.Address;
import com.example.elderexserver.data.address.Amphoe;
import com.example.elderexserver.data.address.District;
import com.example.elderexserver.data.address.Province;
import com.example.elderexserver.data.patient.*;
import com.example.elderexserver.data.patient.DTO.*;
import com.example.elderexserver.data.staff.Staff;
import com.example.elderexserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private BloodTypeRepository bloodTypeRepository;

    @Autowired
    private FoodAllergyRepository foodAllergyRepository;

    @Autowired
    private DrugAllergyRepository drugAllergyRepository;

    @Autowired
    private AmphoeRepository amphoeRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private NationalityRepository nationalityRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private StatusRepository statusRepository;

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
                patient.getProvinceId(),
                patient.getProvince(),
                patient.getAmphoeId(),
                patient.getAmphoe(),
                patient.getDistrictId(),
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

        addressRepository.save(address);

        Gender gender = genderRepository.findById(newPatient.getGenderId())
                .orElseThrow(() -> new IllegalArgumentException("Gender not found"));

        Blood_Type bloodType = bloodTypeRepository.findById(newPatient.getBloodTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Blood type not found"));

        Staff caretaker = staffRepository.findById(newPatient.getCaretakerId())
                .orElseThrow(() -> new IllegalArgumentException("Caretaker not found"));

        Nationality nationality = nationalityRepository.findById(newPatient.getNationalityId())
                .orElseThrow(() -> new IllegalArgumentException("Nationality not found"));

        Hospital hospital = hospitalRepository.findById(newPatient.getPrimaryHospitalId())
                .orElseThrow(() -> new IllegalArgumentException("Primary hospital not found"));

        List<Food_Allergy> foodAllergies = foodAllergyRepository.findAllById(newPatient.getFoodAllergy());

        List<Drug_Allergy> drugAllergies = drugAllergyRepository.findAllById(newPatient.getDrugAllergy());

        List<Medicine> medicine = medicineRepository.findAllById(newPatient.getMedicine());

        Status status = statusRepository.findById(newPatient.getStatusId())
                .orElseThrow(() -> new IllegalArgumentException("Status not found"));

        Patient patient = new Patient(
                newPatient.getCitizenId(),
                newPatient.getFirstName(),
                newPatient.getLastName(),
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
                new HashSet<>(medicine),
                new HashSet<>()
        );

        Patient_Status patientStatus = new Patient_Status(status, LocalDate.now());
        patient.addPatientStatus(patientStatus);

        return patientRepository.save(patient);
    }

    public Patient updatePatient(UpdatePatient updatePatient) {
        Patient patient = patientRepository.findById(updatePatient.getId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        if (updatePatient.getCitizenId() != null) {
            patient.setCitizenId(updatePatient.getCitizenId());
        }

        if (updatePatient.getFirstName() != null) {
            patient.setFirstName(updatePatient.getFirstName());
        }

        if (updatePatient.getLastName() != null) {
            patient.setLastName(updatePatient.getLastName());
        }

        if (updatePatient.getGenderId() != null) {
            Gender gender = genderRepository.findById(updatePatient.getGenderId())
                    .orElseThrow(() -> new IllegalArgumentException("Gender not found"));
            patient.setGender(gender);
        }

        if (updatePatient.getBloodTypeId() != null) {
            Blood_Type bloodType = bloodTypeRepository.findById(updatePatient.getBloodTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Blood type not found"));
            patient.setBloodType(bloodType);
        }

        if (updatePatient.getWeight() != null) {
            patient.setWeight(updatePatient.getWeight());
        }

        if (updatePatient.getHeight() != null) {
            patient.setHeight(updatePatient.getHeight());
        }

        if (updatePatient.getDateOfBirth() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            try {
                LocalDate dob = LocalDate.parse(updatePatient.getDateOfBirth(), formatter);
                patient.setDateOfBirth(dob);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format. Please use dd/MM/yyyy");
            }
        }

        if (updatePatient.getNationalityId() != null) {
            Nationality nationality = nationalityRepository.findById(updatePatient.getNationalityId())
                    .orElseThrow(() -> new IllegalArgumentException("Nationality not found"));
            patient.setNationality(nationality);
        }

        if (updatePatient.getPhone() != null) {
            patient.setPhone(updatePatient.getPhone());
        }

        if (updatePatient.getPicture() != null) {
            patient.setPicture(updatePatient.getPicture());
        }

        if (updatePatient.getCaretakerId() != null) {
            Staff caretaker = staffRepository.findById(updatePatient.getCaretakerId())
                    .orElseThrow(() -> new IllegalArgumentException("Caretaker not found"));
            patient.setCaretaker(caretaker);
        }

        if (updatePatient.getNote() != null) {
            patient.setNote(updatePatient.getNote());
        }

        if (updatePatient.getSurgicalHistory() != null) {
            patient.setSurgicalHistory(updatePatient.getSurgicalHistory());
        }

        if (updatePatient.getPrimaryHospitalId() != null) {
            Hospital hospital = hospitalRepository.findById(updatePatient.getPrimaryHospitalId())
                    .orElseThrow(() -> new IllegalArgumentException("Primary hospital not found"));
            patient.setPrimaryHospital(hospital);
        }

        if (updatePatient.getAddress() != null) {
            patient.getAddress().setAddress(updatePatient.getAddress());
        }

        if (updatePatient.getDistrictId() != null) {
            District district = districtRepository.findById(updatePatient.getDistrictId())
                    .orElseThrow(() -> new IllegalArgumentException("District not found"));
            patient.getAddress().setDistrict(district);
        }

        if (updatePatient.getFoodAllergy() != null) {
            Set<Food_Allergy> foodAllergies = new HashSet<>(foodAllergyRepository.findAllById(updatePatient.getFoodAllergy()));
            patient.setFoodAllergies(foodAllergies);
        }

        if (updatePatient.getDrugAllergy() != null) {
            Set<Drug_Allergy> drugAllergies = new HashSet<>(drugAllergyRepository.findAllById(updatePatient.getDrugAllergy()));
            patient.setDrugAllergies(drugAllergies);
        }

        if (updatePatient.getMedicine() != null) {
            Set<Medicine> medicine = new HashSet<>(medicineRepository.findAllById(updatePatient.getMedicine()));
            patient.setMedicines(medicine);
        }

        if (updatePatient.getStatusId() != null) {
            Status status = statusRepository.findById(updatePatient.getStatusId())
                    .orElseThrow(() -> new IllegalArgumentException("Status not found"));
            patient.addPatientStatus(new Patient_Status(status, LocalDate.now()));
        }

        return patientRepository.save(patient);
    }
}
