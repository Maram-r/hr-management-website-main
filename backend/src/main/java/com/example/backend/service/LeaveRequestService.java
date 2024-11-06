package com.example.backend.service;

import com.example.backend.entity.Employee;
import com.example.backend.entity.LeaveRequest;
import com.example.backend.repository.EmployeeRepository;
import com.example.backend.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestService {
    @Autowired
    private LeaveRequestRepository repository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Récupérer toutes les demandes de congé
    public List<LeaveRequest> getAllLeaveRequests() {
        return repository.findAll();
    }

    // Créer une nouvelle demande de congé
    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest) {
        Optional<Employee> employee = employeeRepository.findById(leaveRequest.getEmployee().getId());
        if (employee.isPresent()) {
            leaveRequest.setEmployee(employee.get());
            leaveRequest.setStatus("Pending"); // Par défaut, le statut est "Pending"
            return repository.save(leaveRequest);
        } else {
            throw new RuntimeException("Employee not found");
        }
    }

    // Mettre à jour le statut d'une demande de congé
    public LeaveRequest updateLeaveRequestStatus(Long id, String status) {
        LeaveRequest leaveRequest = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave Request not found"));

        if (!status.equalsIgnoreCase("Approved") && !status.equalsIgnoreCase("Rejected")) {
            throw new IllegalArgumentException("Status must be either 'Approved' or 'Rejected'");
        }

        leaveRequest.setStatus(status);
        // Ici, vous pouvez appeler une méthode pour notifier l'employé
        notifyEmployee(leaveRequest.getEmployee(), status);

        return repository.save(leaveRequest);
    }

    // Méthode pour notifier l'employé (à implémenter selon vos besoins)
    private void notifyEmployee(Employee employee, String status) {
        String message = "Votre demande de congé a été " + status.toLowerCase() + ".";
        // Implémentez votre logique d'envoi de notification ici (email, SMS, etc.)
        System.out.println("Notification envoyée à " + employee.getEmail() + ": " + message);
    }
}
