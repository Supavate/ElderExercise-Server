package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.DTO.FeaturesRequest;
import com.example.elderexserver.data.address.DTO.AmphoeView;
import com.example.elderexserver.data.address.DTO.DistrictView;
import com.example.elderexserver.data.address.DTO.ProvinceView;
import com.example.elderexserver.data.address.DTO.ZipcodeView;
import com.example.elderexserver.data.exercise.DTO.FeaturesResponse;
import com.example.elderexserver.data.patient.*;
import com.example.elderexserver.data.staff.Role;
import com.example.elderexserver.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {

    private final PublicService publicService;

    // Allergy
    @GetMapping("/allergy/drug")
    public ResponseEntity<List<Drug_Allergy>> getAllDrugAllergies() {
        return ResponseEntity.ok(publicService.getAllDrugAllergies());
    }

    @GetMapping("/allergy/food")
    public ResponseEntity<List<Food_Allergy>> getAllFoodAllergies() {
        return ResponseEntity.ok(publicService.getAllFoodAllergies());
    }

    // Status
    @GetMapping("/status")
    public ResponseEntity<List<Status>> getAllStatus() {
        return ResponseEntity.ok(publicService.getAllStatus());
    }

    // Role
    @GetMapping("/role")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(publicService.getAllRoles());
    }

    // Blood Type
    @GetMapping("/bloodtype")
    public ResponseEntity<List<Blood_Type>> getAllBloodTypes() {
        return ResponseEntity.ok(publicService.getAllBloodTypes());
    }

    // Gender
    @GetMapping("/gender")
    public ResponseEntity<List<Gender>> getAllGenders() {
        return ResponseEntity.ok(publicService.getAllGenders());
    }

    // Province, Amphoe, District, Zipcode
    @GetMapping("/province")
    public ResponseEntity<List<ProvinceView>> getAllProvince() {
        return ResponseEntity.ok(publicService.getAllProvince());
    }

    @GetMapping("/amphoe")
    public ResponseEntity<List<AmphoeView>> getAllAmphoes() {
        return ResponseEntity.ok(publicService.getAllAmphoes());
    }

    @GetMapping("/amphoe/{province}")
    public ResponseEntity<List<AmphoeView>> getAmphoesByProvince(@PathVariable Integer province) {
        return ResponseEntity.ok(publicService.getAmphoesByProvince(province));
    }

    @GetMapping("/district")
    public ResponseEntity<List<DistrictView>> getAllDistrict() {
        return ResponseEntity.ok(publicService.getAllDistrict());
    }

    @GetMapping("/district/{amphoe}")
    public ResponseEntity<List<DistrictView>> getDistrictByAmphoe(@PathVariable Integer amphoe) {
        return ResponseEntity.ok(publicService.getDistrictByAmphoe(amphoe));
    }

    @GetMapping("/zipcode")
    public ResponseEntity<List<ZipcodeView>> getAllZipcode() {
        return ResponseEntity.ok(publicService.getAllZipcode());
    }

    // ML Test
    private final ClassificationService classificationService;

    @PostMapping("/test")
    public FeaturesResponse classifyFeatures(FeaturesRequest request) {
        return classificationService.classify(request);
    }
}
