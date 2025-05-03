package com.example.elderexserver.repository;

import com.example.elderexserver.data.address.Amphoe;
import com.example.elderexserver.data.address.DTO.AmphoeView;
import com.example.elderexserver.data.address.DTO.ZipcodeView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AmphoeRepository extends JpaRepository<Amphoe, Integer> {

    @Query(value = """
        SELECT
                id,
                name
        FROM
                amphoe
        ORDER BY id;
    """, nativeQuery = true)
    List<AmphoeView> getAllAmphoes();

    @Query(value = """
        SELECT
                id,
                zipcode as name
        FROM
                amphoe
        ORDER BY id;
    """, nativeQuery = true)
    List<ZipcodeView> getAllZipcode();
}
