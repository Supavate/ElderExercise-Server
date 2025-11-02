package com.example.elderexserver.service;

import com.example.elderexserver.Exception.ResourceNotFoundException;
import com.example.elderexserver.data.address.DTO.AmphoeView;
import com.example.elderexserver.data.address.DTO.DistrictView;
import com.example.elderexserver.data.address.DTO.ProvinceView;
import com.example.elderexserver.data.address.DTO.ZipcodeView;
import com.example.elderexserver.data.patient.*;
import com.example.elderexserver.data.staff.Role;
import com.example.elderexserver.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicService {

    private final DrugAllergyRepository drugAllergyRepository;
    private final FoodAllergyRepository foodAllergyRepository;
    private final StatusRepository statusRepository;
    private final RoleRepository roleRepository;
    private final BloodTypeRepository bloodTypeRepository;
    private final GenderRepository genderRepository;
    private final ProvinceRepository provinceRepository;
    private final AmphoeRepository amphoeRepository;
    private final DistrictRepository districtRepository;

    public List<Drug_Allergy> getAllDrugAllergies() {
        List<Drug_Allergy> list = drugAllergyRepository.findAll();
        if (list.isEmpty()) throw new ResourceNotFoundException("No drug allergies found");
        return list;
    }

    public List<Food_Allergy> getAllFoodAllergies() {
        List<Food_Allergy> list = foodAllergyRepository.findAll();
        if (list.isEmpty()) throw new ResourceNotFoundException("No food allergies found");
        return list;
    }

    public List<Status> getAllStatus() {
        List<Status> list = statusRepository.findAll();
        if (list.isEmpty()) throw new ResourceNotFoundException("No statuses found");
        return list;
    }

    public List<Role> getAllRoles() {
        List<Role> list = roleRepository.findAll();
        if (list.isEmpty()) throw new ResourceNotFoundException("No roles found");
        return list;
    }

    public List<Blood_Type> getAllBloodTypes() {
        List<Blood_Type> list = bloodTypeRepository.findAll();
        if (list.isEmpty()) throw new ResourceNotFoundException("No blood types found");
        return list;
    }

    public List<Gender> getAllGenders() {
        List<Gender> list = genderRepository.findAll();
        if (list.isEmpty()) throw new ResourceNotFoundException("No genders found");
        return list;
    }

    public List<ProvinceView> getAllProvince() {
        List<ProvinceView> list = provinceRepository.getAllProvince();
        if (list.isEmpty()) throw new ResourceNotFoundException("No provinces found");
        return list;
    }

    public List<AmphoeView> getAllAmphoes() {
        List<AmphoeView> list = amphoeRepository.getAllAmphoes();
        if (list.isEmpty()) throw new ResourceNotFoundException("No amphoes found");
        return list;
    }

    public List<AmphoeView> getAmphoesByProvince(Integer province) {
        List<AmphoeView> list = amphoeRepository.getAmphoesByProvince(province);
        if (list.isEmpty()) throw new ResourceNotFoundException("No amphoes found for province " + province);
        return list;
    }

    public List<DistrictView> getAllDistrict() {
        List<DistrictView> list = districtRepository.getAllDistrict();
        if (list.isEmpty()) throw new ResourceNotFoundException("No districts found");
        return list;
    }

    public List<DistrictView> getDistrictByAmphoe(Integer amphoe) {
        List<DistrictView> list = districtRepository.getDistrictsByAmphoe(amphoe);
        if (list.isEmpty()) throw new ResourceNotFoundException("No districts found for amphoe " + amphoe);
        return list;
    }

    public List<ZipcodeView> getAllZipcode() {
        List<ZipcodeView> list = amphoeRepository.getAllZipcode();
        if (list.isEmpty()) throw new ResourceNotFoundException("No zipcodes found");
        return list;
    }
}
