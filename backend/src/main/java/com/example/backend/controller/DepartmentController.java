package com.example.backend.controller;

import com.example.backend.entity.Department;
import com.example.backend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // Récupérer tous les départements
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    // Récupérer un département par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable int id) {
        Department department = departmentService.getDepartmentById(id);
        if (department != null) {
            return ResponseEntity.ok(department);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Département non trouvé");
    }

    // Ajouter un nouveau département
    @PostMapping
    public ResponseEntity<?> addDepartment(@RequestBody Department department) {
        try {
            Department createdDepartment = departmentService.createDepartment(department);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue lors de l'ajout du département.");
        }
    }

    // Mettre à jour un département existant
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable int id, @RequestBody Department departmentDetails) {
        Department updatedDepartment = departmentService.updateDepartment(id, departmentDetails);
        if (updatedDepartment != null) {
            return ResponseEntity.ok(updatedDepartment);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Département non trouvé");
    }

    // Supprimer un département
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable int id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
