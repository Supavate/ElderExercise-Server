package com.example.elderexserver.repository;

import com.example.elderexserver.data.staff.DTOs.StaffListView;
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
}
