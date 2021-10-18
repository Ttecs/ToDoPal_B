package com.example.mySpringProject1.controller;


import com.example.mySpringProject1.exception.ResourceNotFoundExcption;

import com.example.mySpringProject1.model.AuditModel;
import com.example.mySpringProject1.model.Employee;
import com.example.mySpringProject1.model.Role;
import com.example.mySpringProject1.model.Task;
import com.example.mySpringProject1.repository.EmployeeRepository;
import com.example.mySpringProject1.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    /*@GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return  employeeRepository.findAll();
    }*/
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    @GetMapping("/roles/{roleId}/employees")
    public Page<Employee> getAllEmployeesByRoleId(@PathVariable (value = "roleId") Long roleId,
                                              Pageable pageable) {
        return employeeRepository.findByRoleId(roleId, pageable);
    }

    @PostMapping("/roles/{roleId}/employees")
    public Employee createEmployee(@PathVariable (value = "roleId") Long roleId,
                           @Valid @RequestBody Employee employee) {
        return roleRepository.findById(roleId).map(role -> {
            employee.setRole(role);
            return employeeRepository.save(employee);
        }).orElseThrow(() -> new ResourceNotFoundExcption("roleId " + roleId + " not found"));
    }

    @PostMapping("/employees")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return  employeeRepository.save(employee);
    }

    @PutMapping("/employees/{employeeId}")
    public Employee updateEmployee(@PathVariable Long employeeId, @Valid @RequestBody Employee employeeRequest) {
        return employeeRepository.findById(employeeId).map(employee -> {
            employee.setEname(employeeRequest.getEname());
           employee.setDescription(employeeRequest.getDescription());

            return employeeRepository.save(employee);
        }).orElseThrow(() -> new ResourceNotFoundExcption("employeeId " + employeeId + " not found"));
    }


    @DeleteMapping("/employee/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long employeeId) {
        return employeeRepository.findById(employeeId).map(employee -> {
           employeeRepository.delete(employee);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundExcption("PostId " + employeeId + " not found"));
    }

}