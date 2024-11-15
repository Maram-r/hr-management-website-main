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

    @PostMapping("/{id}/accept")
    public ResponseEntity<Void> acceptLeaveRequest(@PathVariable Long id) {service.updateLeaveRequestStatus(id, "Accepted");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> rejectLeaveRequest(@PathVariable Long id) {
        service.updateLeaveRequestStatus(id, "Rejected");
        return ResponseEntity.ok().build();
    }
}
