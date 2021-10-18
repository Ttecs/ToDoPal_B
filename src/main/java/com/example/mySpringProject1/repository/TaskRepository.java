package com.example.mySpringProject1.repository;



import com.example.mySpringProject1.model.Employee;
import com.example.mySpringProject1.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
   Page<Task> findByEmployeeId(Long employeeId, Pageable pageable);
    Optional<Task> findByIdAndEmployeeId(Long id, Long employeeId);
}