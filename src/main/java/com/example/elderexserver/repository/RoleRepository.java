package com.example.elderexserver.repository;

import com.example.elderexserver.data.staff.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
