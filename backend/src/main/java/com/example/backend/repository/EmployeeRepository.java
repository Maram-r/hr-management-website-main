package com.example.backend.repository;

import com.example.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Object> findById(Long employeeId);
}
