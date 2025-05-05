package com.example.elderexserver.data.patient.DTO;

import com.example.elderexserver.data.patient.Allergy;

import java.util.Set;

public class PatientDetail {
    private Integer id;
    private String picture;
    private String citizen_id;
    private String first_name;
    private String last_name;
    private Integer gender_id;
    private String gender;
    private String date_of_birth;
    private Integer age;
    private Integer blood_type_id;
    private String blood_type;
    private Integer weight;
    private Integer height;
    private String phone;
    private String address;
    private Integer province_id;
    private String province;
    private Integer amphoe_id;
    private String amphoe;
    private Integer district_id;
    private String district;
    private String zipcode;
    private Set<Allergy> allergies;
    private String note;

    public static class Allergy{
        private Integer id;
        private String name;
        private String description;

        public Allergy(Integer id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public PatientDetail(Integer id, String picture, String citizen_id, String first_name, String last_name, Integer gender_id, String gender, String date_of_birth, Integer age, Integer blood_type_id, String blood_type, Integer weight, Integer height, String phone, String address, Integer province_id, String province, Integer amphoe_id, String amphoe, Integer district_id, String district, String zipcode, Set<Allergy> allergies, String note) {
        this.id = id;
        this.picture = picture;
        this.citizen_id = citizen_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender_id = gender_id;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.age = age;
        this.blood_type_id = blood_type_id;
        this.blood_type = blood_type;
        this.weight = weight;
        this.height = height;
        this.phone = phone;
        this.address = address;
        this.province_id = province_id;
        this.province = province;
        this.amphoe_id = amphoe_id;
        this.amphoe = amphoe;
        this.district_id = district_id;
        this.district = district;
        this.zipcode = zipcode;
        this.allergies = allergies;
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCitizen_id() {
        return citizen_id;
    }

    public void setCitizen_id(String citizen_id) {
        this.citizen_id = citizen_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getGender_id() {
        return gender_id;
    }

    public void setGender_id(Integer gender_id) {
        this.gender_id = gender_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getBlood_type_id() {
        return blood_type_id;
    }

    public void setBlood_type_id(Integer blood_type_id) {
        this.blood_type_id = blood_type_id;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getProvince_id() {
        return province_id;
    }

    public void setProvince_id(Integer province_id) {
        this.province_id = province_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getAmphoe_id() {
        return amphoe_id;
    }

    public void setAmphoe_id(Integer amphoe_id) {
        this.amphoe_id = amphoe_id;
    }

    public String getAmphoe() {
        return amphoe;
    }

    public void setAmphoe(String amphoe) {
        this.amphoe = amphoe;
    }

    public Integer getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(Integer district_id) {
        this.district_id = district_id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Set<Allergy> getAllergies() {
        return allergies;
    }

    public void setAllergies(Set<Allergy> allergies) {
        this.allergies = allergies;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
