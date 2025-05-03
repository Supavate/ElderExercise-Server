package com.example.elderexserver.repository;

import com.example.elderexserver.data.staff.DTO.StaffListView;
import com.example.elderexserver.data.staff.DTO.StaffProfileView;
import com.example.elderexserver.data.staff.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Integer> {
    @Query(value = "SELECT * from Staff s", nativeQuery = true)
    List<Staff> findBySupervisor(int id);

    @Query(value = """
        SELECT s.id, s.picture, s.first_name, s.last_name, COUNT(p.id) as patient_count
        FROM Staff s
        LEFT JOIN patient p ON p.caretaker_id = s.id
        GROUP BY s.id, s.picture, s.first_name, s.last_name;
    """, nativeQuery = true)
    List<StaffListView> findStaff();

    @Query(value = """
        SELECT s.id, s.picture, s.first_name, s.last_name, COUNT(p.id) as patient_count
        FROM Staff s
        LEFT JOIN patient p ON p.caretaker_id = s.id
        WHERE s.supervisor_id=:supervisorId
        GROUP BY s.id, s.picture, s.first_name, s.last_name;
    """, nativeQuery = true)
    List<StaffListView> findStaffBySupervisor(int supervisorId);

    @Query(value = """
        SELECT s.id, s.picture, s.first_name, s.last_name, s.gender_id as gender_id, DATE_FORMAT(s.date_of_birth,'%d/%m/%Y') as date_of_birth, r.name as role, s.role_id, r.name, s.email, s.telephone
        FROM Staff s
        LEFT JOIN role r ON r.id = s.role_id
        WHERE s.id=:id;
    """, nativeQuery = true)
    StaffProfileView findStaffProfileById(int id);
}
