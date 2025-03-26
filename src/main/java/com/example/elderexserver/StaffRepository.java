package com.example.elderexserver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Integer> {
    @Query(value = "SELECT * from Staff s where s.superviser=:id", nativeQuery = true)
    List<Staff> findBySupervisor(int id);
}
