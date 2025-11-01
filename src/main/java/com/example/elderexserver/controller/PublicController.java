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
import org.springframework.web.bind.annotation.PathVariable;
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
        try {
            List<Drug_Allergy> drugAllergies = drugAllergyRepository.findAll();
            if (drugAllergies.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(drugAllergies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Autowired
    private FoodAllergyRepository foodAllergyRepository;

    @GetMapping("/allergy/food")
    public ResponseEntity<List<Food_Allergy>> getAllFoodAllergies() {
        try {
            List<Food_Allergy> foodAllergies = foodAllergyRepository.findAll();
            if (foodAllergies.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(foodAllergies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Status
    @Autowired
    private StatusRepository statusRepository;

    @GetMapping("/status")
    public ResponseEntity<List<Status> >getAllStatus() {
        try {
            List<Status> statusList = statusRepository.findAll();
            if (statusList.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(statusList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    //Role
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/role")
    public ResponseEntity<List<Role>> getAllRoles() {
        try {
            List<Role> roles = roleRepository.findAll();
            if (roles.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //BloodType
    @Autowired
    private BloodTypeRepository bloodTypeRepository;

    @GetMapping("/bloodtype")
    public ResponseEntity<List<Blood_Type>> getAllBloodTypes() {
        try {
            List<Blood_Type> bloodTypes = bloodTypeRepository.findAll();
            if (bloodTypes.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(bloodTypes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Gender
    @Autowired
    private GenderRepository genderRepository;

    @GetMapping("/gender")
    public ResponseEntity<List<Gender>> getAllGenders() {
        try {
            List<Gender> genders = genderRepository.findAll();
            if (genders.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(genders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private AmphoeRepository amphoeRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @GetMapping("/zipcode")
    public ResponseEntity<List<ZipcodeView>> getAllZipcode() {
        try {
            List<ZipcodeView> zipcodeViews = amphoeRepository.getAllZipcode();
            if (zipcodeViews.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(zipcodeViews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/amphoe")
    public ResponseEntity<List<AmphoeView>> getAllAmphoes() {
        try {
            List<AmphoeView> amphoeViews = amphoeRepository.getAllAmphoes();
            if (amphoeViews.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(amphoeViews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/amphoe/{province}")
    public ResponseEntity<List<AmphoeView>> getAmphoesByProvince(@PathVariable Integer province) {
        try {
            List<AmphoeView> amphoeViews = amphoeRepository.getAmphoesByProvince(province);
            if (amphoeViews.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(amphoeViews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/district")
    public ResponseEntity<List<DistrictView>> getAllDistrict() {
        try {
            List<DistrictView> districtViews = districtRepository.getAllDistrict();
            if (districtViews.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(districtViews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/district/{amphoe}")
    public ResponseEntity<List<DistrictView>> getDistrictByAmphoe(@PathVariable Integer amphoe) {
        try {
            List<DistrictView> districtViews = districtRepository.getDistrictsByAmphoe(amphoe);
            if (districtViews.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(districtViews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/Province")
    public ResponseEntity<List<ProvinceView>> getAllProvince() {
        try {
            List<ProvinceView> provinceViews = provinceRepository.getAllProvince();
            if (provinceViews.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(provinceViews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}