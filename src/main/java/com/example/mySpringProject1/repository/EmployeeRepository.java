package com.example.mySpringProject1.repository;


import com.example.mySpringProject1.model.AuditModel;
import com.example.mySpringProject1.model.Employee;

import com.example.mySpringProject1.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

        Page<Employee> findByRoleId(Long roleId, Pageable pageable);
        Optional<Employee> findByIdAndRoleId(Long id, Long roleId);


}