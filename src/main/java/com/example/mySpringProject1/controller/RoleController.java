package com.example.mySpringProject1.controller;


import com.example.mySpringProject1.exception.ResourceNotFoundExcption;
import com.example.mySpringProject1.model.Role;
import com.example.mySpringProject1.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/roles")
    @CrossOrigin(origins="http://localhost:3000")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @PostMapping("/roles")
    @CrossOrigin(origins="http://localhost:3000")
    public Role createRole(@Valid @RequestBody Role role) {
        return roleRepository.save(role);
    }

    @PutMapping("/roles/{roleId}")
    public Role updateRole(@PathVariable Long roleId, @Valid @RequestBody Role roleRequest) {
        return roleRepository.findById(roleId).map(role -> {
            role.setRolename(roleRequest.getRolename());
            role.setDescription(roleRequest.getDescription());

            return roleRepository.save(role);
        }).orElseThrow(() -> new ResourceNotFoundExcption("RoleId " + roleId + " not found"));
    }


    @DeleteMapping("/roles/{roleId}")
    @CrossOrigin(origins="http://localhost:3000")
    public ResponseEntity<?> deleteRole(@PathVariable Long roleId) {
        return roleRepository.findById(roleId).map(role -> {
            roleRepository.delete(role);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundExcption("RoleId " + roleId + " not found"));
    }

}