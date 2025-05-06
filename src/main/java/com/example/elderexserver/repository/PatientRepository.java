package com.example.elderexserver.repository;

import com.example.elderexserver.data.patient.DTO.*;
import com.example.elderexserver.data.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query(value = "SELECT *, TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) AS age FROM Patient p WHERE p.id=:id;", nativeQuery = true)
    Patient findById(int id);

    @Query(value = "SELECT id, first_name, last_name, weight, height, TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) AS age FROM patient;", nativeQuery = true)
    List<PatientWithAgeView> findAllWithAge();

    @Query(value = """
        Select p.id, first_name, last_name, weight, height, GROUP_CONCAT(a.name) AS allergies FROM Patient p
        LEFT JOIN patient_allergy ON patient_allergy.patient_id = p.id
        LEFT JOIN allergy a ON a.id = patient_allergy.allergy_id
        GROUP BY p.id;
    """, nativeQuery = true)
    List<PatientWithAllergiesView> findAllWithAllergies();

    @Query(value = """
        Select p.id, p.picture, p.first_name, p.last_name, g.name as gender, TIMESTAMPDIFF(YEAR, p.date_of_birth, CURDATE()) AS age FROM Patient p
        LEFT JOIN gender g ON p.gender_id = g.id
        WHERE p.caretaker_id =:caretakerId
    """, nativeQuery = true)
    List<PatientFromCaretakerIdView> findAllByCaretakerId(int caretakerId);

    @Query(value = """
        SELECT
                          p.id,
                          p.picture,
                          p.citizen_id,
                          p.first_name,
                          p.last_name,
                          p.gender_id,
                          g.name AS gender,
                          DATE_FORMAT(p.date_of_birth, '%d/%m/%Y') AS date_of_birth,
                          a.id AS allergy_id,
                          a.name AS allergy_name,
                          a.description AS allergy_description,
                          bt.id AS blood_type_id,
                          bt.type AS blood_type,
                          p.weight,
                          p.height,
                          p.phone,
                          adr.address,
                        prov.id AS province_id,
                        prov.name AS province,
                        amp.id AS amphoe_id,
                        amp.name AS amphoe,
                        d.id AS district_id,
                        d.name AS district,
                          amp.zipcode AS zipcode,
                          p.note,
                          TIMESTAMPDIFF(
                              YEAR,
                              p.date_of_birth,
                              CURDATE()) AS age
                          FROM
                              patient p
                          LEFT JOIN gender g ON
                              p.gender_id = g.id
                          LEFT JOIN blood_type bt ON
                              p.blood_type_id = bt.id
                          LEFT JOIN patient_allergy pa ON
                              p.id = pa.patient_id
                          LEFT JOIN allergy a ON
                              pa.allergy_id = a.id
                          LEFT JOIN address adr ON
                              p.address_id = adr.id
                          LEFT JOIN district d ON
                              adr.district_id = d.id
                          LEFT JOIN amphoe amp ON
                              d.amphoe_id = amp.id
                          LEFT JOIN province prov ON
                              amp.province_id = prov.id
                          WHERE
                              p.id =:id;
    """, nativeQuery = true)
    List<PatientDetailView> findPatientDetailById(int id);

    @Query(value = """
    SELECT
            p.id,
            p.first_name,
            p.last_name,
            s.first_name as caretaker_first_name,
            s.last_name as caretaker_last_name,
            p.picture
    FROM
            patient p
    LEFT JOIN
            staff s ON p.caretaker_id = s.id;
    """, nativeQuery = true)
    List<PatientListView> findPatientList();
}
