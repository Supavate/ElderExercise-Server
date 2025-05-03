package com.example.elderexserver.repository;

import com.example.elderexserver.data.address.DTO.ProvinceView;
import com.example.elderexserver.data.address.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProvinceRepository extends JpaRepository<Province, Integer> {
    @Query(value = """
        SELECT
                id,
                name
        FROM
                province
        ORDER BY id;
    """, nativeQuery = true)
    List<ProvinceView> getAllProvince();
}
