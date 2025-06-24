package com.example.elderexserver.repository;

import com.example.elderexserver.data.patient.DTO.*;
import com.example.elderexserver.data.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query(value = "SELECT id, first_name, last_name, weight, height, TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) AS age FROM patient;", nativeQuery = true)
    List<PatientWithAgeView> findAllWithAge();

    @Query(value = """
        SELECT
            p.id,
            first_name,
            last_name,
            weight,
            height,
            GROUP_CONCAT(a.name) AS allergies
        FROM
            Patient p
        LEFT JOIN patient_allergy ON patient_allergy.patient_id = p.id
        LEFT JOIN allergy a ON
            a.id = patient_allergy.allergy_id
        GROUP BY
            p.id;
    """, nativeQuery = true)
    List<PatientWithAllergiesView> findAllWithAllergies();

    @Query(value = """
        SELECT
            p.id,
            p.picture,
            p.first_name,
            p.last_name,
            g.name AS gender,
            TIMESTAMPDIFF(
                YEAR,
                p.date_of_birth,
                CURDATE()) AS age
            FROM
                Patient p
            LEFT JOIN gender g ON
                p.gender_id = g.id
            WHERE
                p.caretaker_id = :caretakerId
    """, nativeQuery = true)
    List<PatientFromCaretakerIdView> findAllByCaretakerId(int caretakerId);

    @Query(value = """
        SELECT
            p.id,
            p.citizen_id,
            p.picture,
            p.first_name,
            p.last_name,
            p.gender_id,
            g.name AS gender,
            p.blood_type_id,
            bt.type AS blood_type,
            p.weight,
            p.height,
            ROUND(p.weight / POWER(p.height / 100, 2), 3) AS BMI,
            DATE_FORMAT(p.date_of_birth, '%d/%m/%Y') AS date_of_birth,
            TIMESTAMPDIFF(
                YEAR,
                p.date_of_birth,
                CURDATE()) AS age,
            p.nationality_id,
            n.name as nationality,
            p.phone,
            p.note,
            p.surgical_history,
            p.primary_hospital_id,
            h.name as primaryHospital,
            adr.address,
            prov.id AS province_id,
            prov.name AS province,
            amp.id AS amphoe_id,
            amp.name AS amphoe,
            d.id AS district_id,
            d.name AS district,
            amp.zipcode AS zipcode,
            fa.id AS food_id,
            fa.name AS food_name,
            fa.description AS food_description,
            da.id AS drug_id,
            da.name AS drug_name,
            da.description AS drug_description,
            m.id AS medicine_id,
            m.name AS medicine_name,
            m.description AS medicine_description
        FROM
            patient p
            LEFT JOIN gender g ON
                p.gender_id = g.id
            LEFT JOIN blood_type bt ON
                p.blood_type_id = bt.id
            LEFT JOIN nationality n ON
                p.nationality_id = n.id
            LEFT JOIN address adr ON
                p.address_id = adr.id
            LEFT JOIN district d ON
                adr.district_id = d.id
            LEFT JOIN amphoe amp ON
                d.amphoe_id = amp.id
            LEFT JOIN province prov ON
                amp.province_id = prov.id
            LEFT JOIN patient_food_allergy pfa ON
                p.id = pfa.patient_id
            LEFT JOIN food_allergy fa
                pfa.food_allergy_id = fa.id
            LEFT JOIN patient_drug_allergy pda ON
                p.id = pda.patient_id
            LEFT JOIN drug_allergy da ON
                pda.drug_allergy_id = da.id
        WHERE
            p.id =:id;
    """, nativeQuery = true)
    List<PatientDetailView> findPatientDetailById(int id);

    @Query(value = """
    SELECT
            p.id,
            p.first_name,
            p.last_name,
            TIMESTAMPDIFF(YEAR, p.date_of_birth, CURDATE()) AS age,
            ROUND(p.weight / POWER(p.height / 100, 2), 3) AS BMI,
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
