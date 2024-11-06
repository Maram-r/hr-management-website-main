package com.example.backend.service;

import com.example.backend.entity.DocumentRequest;
import com.example.backend.entity.Employee;
import com.example.backend.repository.DocumentRequestRepository;
import com.example.backend.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentRequestService {

    @Autowired
    private DocumentRequestRepository documentRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<DocumentRequest> getAllDocumentRequests() {
        return documentRequestRepository.findAll();
    }

    public DocumentRequest createDocumentRequest(DocumentRequest documentRequest) {
        Optional<Employee> employee = employeeRepository.findById(documentRequest.getEmployee().getId());
        if (employee.isPresent()) {
            documentRequest.setEmployee(employee.get());
            return documentRequestRepository.save(documentRequest);
        } else {
            throw new RuntimeException("Employee not found");
        }
    }

    public DocumentRequest updateDocumentRequest(Long id, DocumentRequest updatedRequest) {
        DocumentRequest documentRequest = documentRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document Request not found"));

        // Met à jour les champs nécessaires
        documentRequest.setDocumentType(updatedRequest.getDocumentType());
        documentRequest.setRequestDate(updatedRequest.getRequestDate());
        documentRequest.setEmployee(updatedRequest.getEmployee()); // S'assurer que l'employé existe
        return documentRequestRepository.save(documentRequest);
    }

    public void deleteDocumentRequest(Long id) {
        documentRequestRepository.deleteById(id);
    }
    public void updateRequestStatus(Long id, String status) throws Exception {
        Optional<DocumentRequest> optionalRequest = documentRequestRepository.findById(id);
        if (optionalRequest.isPresent()) {
            DocumentRequest request = optionalRequest.get();
            request.setStatus(status);  // Mettez à jour le statut
            documentRequestRepository.save(request);  // Enregistrez la demande mise à jour
        } else {
            throw new Exception("Demande introuvable.");
        }
    }


    public void acceptRequest(Long id) throws Exception {
        // Logique pour traiter l'acceptation d'une demande de document
        DocumentRequest request = documentRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Demande non trouvée"));

        request.setStatus("Accepted");
        documentRequestRepository.save(request);
        // Vous pouvez aussi ajouter une redirection ou une notification ici si nécessaire
    }
}