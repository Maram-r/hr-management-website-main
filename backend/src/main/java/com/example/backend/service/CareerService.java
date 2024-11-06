package com.example.backend.service;

import com.example.backend.entity.Career;
import com.example.backend.entity.Employee;
import com.example.backend.entity.Role;
import com.example.backend.repository.CareerRepository;
import com.example.backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CareerService {

    private final CareerRepository careerRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public CareerService(CareerRepository careerRepository, EmployeeRepository employeeRepository) {
        this.careerRepository = careerRepository;
        this.employeeRepository = employeeRepository;
    }

    // Créer ou mettre à jour une entrée Career avec validation de l'Employee
    public Career saveCareer(Career career) {
        Optional<Employee> employeeOptional = employeeRepository.findById(career.getEmployee().getId());

        if (employeeOptional.isPresent()) {
            career.setEmployee(employeeOptional.get()); // Associe l'employee s'il est trouvé
            return careerRepository.save(career);
        } else {
            throw new RuntimeException("Employee not found"); // Gérer l'absence de l'employee
        }
    }

    // Récupérer une Career par ID
    public Optional<Career> getCareerById(Long id) {
        return careerRepository.findById(id);
    }

    // Récupérer toutes les entrées de Career
    public List<Career> getAllCareers() {
        return careerRepository.findAll();
    }

    // Supprimer une Career par ID
    public void deleteCareer(Long id) {
        careerRepository.deleteById(id);
    }

    // Affecter un Employee et un Role à une Career
    public void affectation(Employee employee, Career career, Role role) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        if (employeeOptional.isPresent()) {
            career.setEmployee(employeeOptional.get());
            employee.setRole(role); // Mise à jour du rôle de l'employé
            careerRepository.save(career);
        } else {
            throw new RuntimeException("Employee not found");
        }
    }

    // Ajouter une action disciplinaire à une Career
    public void manageDiscipline(Career career, String discipline) {
        career.setDiscipline(discipline); // Mise à jour de la discipline
        careerRepository.save(career);
    }

    // Gérer le départ d'un Employee dans une Career
    public void handleDeparture(Career career) {
        career.setDepartureDate(LocalDate.now());
        careerRepository.save(career);
    }
}