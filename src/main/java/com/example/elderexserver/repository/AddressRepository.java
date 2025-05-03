package com.example.elderexserver.repository;

import com.example.elderexserver.data.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
