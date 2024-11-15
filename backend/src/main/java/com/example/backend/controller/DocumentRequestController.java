package com.example.backend.controller;

import com.example.backend.entity.DocumentRequest;
import com.example.backend.repository.EmployeeRepository;
import com.example.backend.service.DocumentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/document-requests")
public class DocumentRequestController {

    @Autowired
    private DocumentRequestService service;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity<List<DocumentRequest>> getAllDocumentRequests() {
        List<DocumentRequest> documentRequests = service.getAllDocumentRequests();
        return ResponseEntity.ok(documentRequests);
    }

    @PostMapping
    public ResponseEntity<String> createDocumentRequest(@RequestBody Map<String, Object> requestData) {
        // Vérification des données
        if (!requestData.containsKey("documentType") || !requestData.containsKey("employeeId")) {
            return ResponseEntity.badRequest().body("Les champs 'documentType' et 'employeeId' sont obligatoires.");
        }

        try {
            String documentType = (String) requestData.get("documentType");
            Long employeeId = Long.valueOf(requestData.get("employeeId").toString());

            // Vérification de l'existence de l'employé
            var employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

            // Création de la demande de document
            DocumentRequest request = new DocumentRequest();
            request.setDocumentType(documentType);
            request.setEmployee(employee);
            request.setStatus("en attente");

            service.createDocumentRequest(request);
            return ResponseEntity.ok("Demande de document soumise avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la soumission de la demande : " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentRequest> updateDocumentRequest(
            @PathVariable Long id,
            @RequestBody DocumentRequest updatedRequest) {
        Optional<DocumentRequest> existingRequest = service.findById(id);

        if (existingRequest.isPresent()) {
            DocumentRequest updatedDocumentRequest = service.updateDocumentRequest(id, updatedRequest);
            return ResponseEntity.ok(updatedDocumentRequest);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<String> acceptRequest(@PathVariable Long id) {
        try {
            service.acceptRequest(id);
            return ResponseEntity.ok("Demande acceptée avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Demande non trouvée");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'acceptation de la demande : " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocumentRequest(@PathVariable Long id) {
        try {
            service.deleteDocumentRequest(id);
            return ResponseEntity.ok("Demande supprimée avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Demande non trouvée");
        }
    }
}
