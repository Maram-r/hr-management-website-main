package com.example.backend.service;

import com.example.backend.entity.Department;
import com.example.backend.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Récupérer tous les départements
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Récupérer un département par ID
    public Department getDepartmentById(int id) {
        Optional<Department> department = departmentRepository.findById(id);
        return department.orElse(null);
    }

    // Créer un nouveau département
    public Department createDepartment(Department department) {
        // Vérification de l'unicité du nom du département
        if (departmentRepository.existsByName(department.getName())) {
            throw new IllegalArgumentException("Un département avec ce nom existe déjà.");
        }
        return departmentRepository.save(department);
    }

    // Mettre à jour un département existant
    public Department updateDepartment(int id, Department departmentDetails) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            Department existingDepartment = department.get();
            existingDepartment.setName(departmentDetails.getName());
            existingDepartment.setDescription(departmentDetails.getDescription());
            return departmentRepository.save(existingDepartment);
        }
        return null;
    }

    // Supprimer un département
    public void deleteDepartment(int id) {
        departmentRepository.deleteById(id);
    }
}
