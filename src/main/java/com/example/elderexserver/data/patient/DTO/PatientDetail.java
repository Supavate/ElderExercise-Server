package com.example.elderexserver.data.patient.DTO;

import java.util.Set;

public class PatientDetail {
    private Integer id;
    private String citizen_id;
    private String first_name;
    private String last_name;
    private String email;
    private Integer gender_id;
    private String gender;
    private Integer blood_type_id;
    private String blood_type;
    private Integer weight;
    private Integer height;
    private Float bmi;
    private String date_of_birth;
    private Integer age;
    private Integer nationality_id;
    private String nationality;
    private String phone;
    private String picture;
    private String note;
    private String surgicalHistory;
    private Integer primary_hospital_id;
    private String primaryHospital;
    private String address;
    private Integer province_id;
    private String province;
    private Integer amphoe_id;
    private String amphoe;
    private Integer district_id;
    private String district;
    private String zipcode;
    private Set<Allergy> food_allergies;
    private Set<Allergy> drug_allergies;
    private Set<Medicine> medicines;


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

    public static class Medicine{
        private Integer id;
        private String name;
        private String description;

        public Medicine(Integer id, String name, String description) {
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

    public PatientDetail(Integer id, String citizen_id, String first_name, String last_name, String email, Integer gender_id, String gender, Integer blood_type_id, String blood_type, Integer weight, Integer height, Float bmi, String date_of_birth, Integer age, Integer nationality_id, String nationality, String phone, String picture, String note, String surgicalHistory, Integer primary_hospital_id, String primaryHospital, String address, Integer province_id, String province, Integer amphoe_id, String amphoe, Integer district_id, String district, String zipcode, Set<Allergy> food_allergies, Set<Allergy> drug_allergies, Set<Medicine> medicines) {
        this.id = id;
        this.citizen_id = citizen_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.gender_id = gender_id;
        this.gender = gender;
        this.blood_type_id = blood_type_id;
        this.blood_type = blood_type;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.date_of_birth = date_of_birth;
        this.age = age;
        this.nationality_id = nationality_id;
        this.nationality = nationality;
        this.phone = phone;
        this.picture = picture;
        this.note = note;
        this.surgicalHistory = surgicalHistory;
        this.primary_hospital_id = primary_hospital_id;
        this.primaryHospital = primaryHospital;
        this.address = address;
        this.province_id = province_id;
        this.province = province;
        this.amphoe_id = amphoe_id;
        this.amphoe = amphoe;
        this.district_id = district_id;
        this.district = district;
        this.zipcode = zipcode;
        this.food_allergies = food_allergies;
        this.drug_allergies = drug_allergies;
        this.medicines = medicines;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Float getBmi() {
        return bmi;
    }

    public void setBmi(Float bmi) {
        this.bmi = bmi;
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

    public Integer getNationality_id() {
        return nationality_id;
    }

    public void setNationality_id(Integer nationality_id) {
        this.nationality_id = nationality_id;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSurgicalHistory() {
        return surgicalHistory;
    }

    public void setSurgicalHistory(String surgicalHistory) {
        this.surgicalHistory = surgicalHistory;
    }

    public Integer getPrimary_hospital_id() {
        return primary_hospital_id;
    }

    public void setPrimary_hospital_id(Integer primary_hospital_id) {
        this.primary_hospital_id = primary_hospital_id;
    }

    public String getPrimaryHospital() {
        return primaryHospital;
    }

    public void setPrimaryHospital(String primaryHospital) {
        this.primaryHospital = primaryHospital;
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

    public Set<Allergy> getFood_allergies() {
        return food_allergies;
    }

    public void setFood_allergies(Set<Allergy> food_allergies) {
        this.food_allergies = food_allergies;
    }

    public Set<Allergy> getDrug_allergies() {
        return drug_allergies;
    }

    public void setDrug_allergies(Set<Allergy> drug_allergies) {
        this.drug_allergies = drug_allergies;
    }

    public Set<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(Set<Medicine> medicines) {
        this.medicines = medicines;
    }
}
