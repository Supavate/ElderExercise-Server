package com.example.elderexserver.controller;

import com.example.elderexserver.data.address.Amphoe;
import com.example.elderexserver.data.address.DTO.AmphoeView;
import com.example.elderexserver.data.address.DTO.DistrictView;
import com.example.elderexserver.data.address.DTO.ProvinceView;
import com.example.elderexserver.data.address.DTO.ZipcodeView;
import com.example.elderexserver.data.patient.*;
import com.example.elderexserver.repository.*;
import com.example.elderexserver.data.staff.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    //Allergy
    @Autowired
    private AllergyRepository allergyRepository;

    @GetMapping("/allergy")
    public List<Allergy> getAllAllergies() {return allergyRepository.findAll();}

    //Status
    @Autowired
    private StatusRepository statusRepository;

    @GetMapping("/status")
    public List<Status> getAllStatus() {return statusRepository.findAll();}

    //Role
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/role")
    public List<Role> getAllRoles() {return roleRepository.findAll();}

    //BloodType
    @Autowired
    private BloodTypeRepository bloodTypeRepository;

    @GetMapping("/bloodtype")
    public List<Blood_Type> getAllBloodTypes() {return bloodTypeRepository.findAll();}

    //Gender
    @Autowired
    private GenderRepository genderRepository;

    @GetMapping("/gender")
    public List<Gender> getAllGenders() {return genderRepository.findAll();}

    //Address
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private AmphoeRepository amphoeRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @GetMapping("/zipcode")
    public List<ZipcodeView> getAllZipcode() {
        return amphoeRepository.getAllZipcode();
    }

    @GetMapping("/amphoe")
    public List<AmphoeView> getAllAmphoes() {
        return amphoeRepository.getAllAmphoes();
    }

    @GetMapping("/district")
    public List<DistrictView> getAllDistrict() {
        return districtRepository.getAllDistrict();
    }

    @GetMapping("/Province")
    public List<ProvinceView> getAllProvince() {
        return provinceRepository.getAllProvince();
    }
}
