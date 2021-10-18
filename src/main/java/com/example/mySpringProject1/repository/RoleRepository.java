package com.example.mySpringProject1.repository;

import com.example.mySpringProject1.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
