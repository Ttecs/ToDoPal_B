package com.example.mySpringProject1.controller;

import com.example.mySpringProject1.exception.ResourceNotFoundExcption;

import com.example.mySpringProject1.model.Task;
import com.example.mySpringProject1.repository.TaskRepository;

import com.example.mySpringProject1.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees/{employeeId}/tasks")
    @CrossOrigin(origins="http://localhost:3000")
    public Page<Task> getAllTasksByEmployeeId(@PathVariable (value = "employeeId") Long employeeId,
                                              Pageable pageable) {
        return taskRepository.findByEmployeeId(employeeId, pageable);
    }

    @PostMapping("/employees/{employeeId}/tasks")
    @CrossOrigin(origins="http://localhost:3000")
    public Task createTask(@PathVariable (value = "employeeId") Long employeeId,
                                 @Valid @RequestBody Task task) {
        return employeeRepository.findById(employeeId).map(employee -> {
            task.setEmployee(employee);
            return taskRepository.save(task);
        }).orElseThrow(() -> new ResourceNotFoundExcption("employeeId " + employeeId + " not found"));
    }

    @PutMapping("/employees/{employeeId}/tasks/{taskId}")
    @CrossOrigin(origins="http://localhost:3000")
    public Task updateTask(@PathVariable (value = "employeeId") Long employeeId,
                                 @PathVariable (value = "taskId") Long taskId,
                                 @Valid @RequestBody Task taskRequest) {
        if(!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundExcption("employeeId " + employeeId + " not found");
        }

        return taskRepository.findById(taskId).map(task -> {
            task.setText(taskRequest.getText());
            return taskRepository.save(task);
        }).orElseThrow(() -> new ResourceNotFoundExcption("taskId " + taskId + "not found"));
    }

    @DeleteMapping("/employees/{employeeId}/tasks/{taskId}")
    @CrossOrigin(origins="http://localhost:3000")
    public ResponseEntity<?> deleteTask(@PathVariable (value = "employeeId") Long employeeId,
                                           @PathVariable (value = "taskId") Long taskId) {
        return taskRepository.findByIdAndEmployeeId(taskId, employeeId).map(task -> {
           taskRepository.delete(task);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundExcption("Comment not found with id " + taskId + " and employeeId " + employeeId));
    }
}