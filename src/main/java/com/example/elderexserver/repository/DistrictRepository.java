package com.example.elderexserver.repository;

import com.example.elderexserver.data.address.DTO.DistrictView;
import com.example.elderexserver.data.address.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    @Query(value = """
        SELECT
                id,
                name
        FROM
                district
        ORDER BY id;
    """, nativeQuery = true)
    List<DistrictView> getAllDistrict();

    @Query(value = """
        SELECT
                id,
                name
        FROM
                district
        WHERE
                amphoe_id = :amphoe_id
        ORDER BY id;
    """, nativeQuery = true)
    List<DistrictView> getDistrictsByAmphoe(Integer amphoe);
}
