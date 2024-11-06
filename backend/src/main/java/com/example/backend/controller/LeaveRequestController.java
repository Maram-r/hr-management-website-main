package com.example.backend.controller;

import com.example.backend.entity.LeaveRequest;
import com.example.backend.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/leaves")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService service;

    // Récupérer toutes les demandes de congé
    @GetMapping
    public List<LeaveRequest> getAllLeaveRequests() {
        return service.getAllLeaveRequests();
    }

    // Créer une nouvelle demande de congé
    @PostMapping
    public ResponseEntity<?> createLeaveRequest(@RequestBody LeaveRequest leaveRequest) {
        try {
            LeaveRequest savedRequest = service.createLeaveRequest(leaveRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Demande de congé enregistrée avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de la soumission de la demande : " + e.getMessage());
        }
    }

    // Mettre à jour le statut d'une demande de congé (approuver ou rejeter)
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateLeaveRequestStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            LeaveRequest updatedRequest = service.updateLeaveRequestStatus(id, status);
            if (status.equalsIgnoreCase("Approved")) {
                // Logique pour envoyer une notification à l'employé
                // Par exemple : envoyerNotification(updatedRequest.getEmployee(), "Votre demande de congé a été approuvée.");
            } else if (status.equalsIgnoreCase("Rejected")) {
                // Logique pour envoyer une notification à l'employé
                // Par exemple : envoyerNotification(updatedRequest.getEmployee(), "Votre demande de congé a été rejetée.");
            }
            return ResponseEntity.ok(updatedRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de la mise à jour du statut : " + e.getMessage());
        }
    }
}
