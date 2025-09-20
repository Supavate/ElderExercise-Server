package com.example.elderexserver.controller;

import com.example.elderexserver.data.address.DTO.AmphoeView;
import com.example.elderexserver.data.address.DTO.DistrictView;
import com.example.elderexserver.data.address.DTO.ProvinceView;
import com.example.elderexserver.data.address.DTO.ZipcodeView;
import com.example.elderexserver.data.patient.*;
import com.example.elderexserver.repository.*;
import com.example.elderexserver.data.staff.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/public")
public class PublicController {

    //Allergy
    @Autowired
    private DrugAllergyRepository drugAllergyRepository;

    @GetMapping("/allergy/drug")
    public ResponseEntity<List<Drug_Allergy>> getAllDrugAllergies() {
        List<Drug_Allergy> drugAllergies = drugAllergyRepository.findAll();
        if (drugAllergies.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(drugAllergies);
    }

    @Autowired
    private FoodAllergyRepository foodAllergyRepository;

    @GetMapping("/allergy/food")
    public ResponseEntity<List<Food_Allergy>> getAllFoodAllergies() {
        List<Food_Allergy> foodAllergies = foodAllergyRepository.findAll();
        if (foodAllergies.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(foodAllergies);
    }

    //Status
    @Autowired
    private StatusRepository statusRepository;

    @GetMapping("/status")
    public ResponseEntity<List<Status> >getAllStatus() {
        List<Status> statusList = statusRepository.findAll();
        if (statusList.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(statusList);
    }


    //Role
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/role")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(roles);
    }

    //BloodType
    @Autowired
    private BloodTypeRepository bloodTypeRepository;

    @GetMapping("/bloodtype")
    public ResponseEntity<List<Blood_Type>> getAllBloodTypes() {
        List<Blood_Type> bloodTypes = bloodTypeRepository.findAll();
        if (bloodTypes.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bloodTypes);
    }

    //Gender
    @Autowired
    private GenderRepository genderRepository;

    @GetMapping("/gender")
    public ResponseEntity<List<Gender>> getAllGenders() {
        List<Gender> genders = genderRepository.findAll();
        if (genders.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(genders);
    }

    //Address

//    @Autowired
//    private AddressRepository addressRepository; Private information

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private AmphoeRepository amphoeRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @GetMapping("/zipcode")
    public ResponseEntity<List<ZipcodeView>> getAllZipcode() {
        List<ZipcodeView> zipcodeViews = amphoeRepository.getAllZipcode();
        if (zipcodeViews.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(zipcodeViews);
    }

    @GetMapping("/amphoe")
    public ResponseEntity<List<AmphoeView>> getAllAmphoes() {
        List<AmphoeView> amphoeViews = amphoeRepository.getAllAmphoes();
        if (amphoeViews.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(amphoeViews);
    }

    @GetMapping("/district")
    public ResponseEntity<List<DistrictView>> getAllDistrict() {
        List<DistrictView> districtViews = districtRepository.getAllDistrict();
        if (districtViews.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(districtViews);
    }

    @GetMapping("/Province")
    public ResponseEntity<List<ProvinceView>> getAllProvince() {
        List<ProvinceView> provinceViews = provinceRepository.getAllProvince();
        if (provinceViews.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(provinceViews);
    }
}