package com.example.elderexserver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientsRepository extends JpaRepository<Patients, Integer> {

    @Query(value = "select * from Patients p where p.Main_Caretaker=:Staff_ID", nativeQuery = true)
    List<Patients> getPatientsByStaff(int Staff_ID);

    @Query(value = "SELECT *, TIMESTAMPDIFF(YEAR, Date_of_Birth, CURDATE()) AS Age FROM Patients;", nativeQuery = true)
    List<Patients> getPatientsAge();
}
