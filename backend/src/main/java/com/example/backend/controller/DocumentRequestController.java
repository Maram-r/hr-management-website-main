package com.example.backend.controller;

import com.example.backend.entity.DocumentRequest;
import com.example.backend.service.DocumentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/document-requests")
public class DocumentRequestController {

    @Autowired
    private DocumentRequestService service;

    @GetMapping
    public List<DocumentRequest> getAllDocumentRequests() {
        return service.getAllDocumentRequests();
    }

    @PostMapping
    public DocumentRequest createDocumentRequest(@RequestBody DocumentRequest documentRequest) {
        return service.createDocumentRequest(documentRequest);
    }

    @PutMapping("/{id}")
    public DocumentRequest updateDocumentRequest(@PathVariable Long id, @RequestBody DocumentRequest updatedRequest) {
        return service.updateDocumentRequest(id, updatedRequest);
    }
    @PostMapping("/{id}/accept")
    public ResponseEntity<Void> acceptRequest(@PathVariable Long id) throws Exception {
        service.acceptRequest(id);
        return ResponseEntity.status(200).build();  // OK sans redirection
    }
    @DeleteMapping("/{id}")
    public void deleteDocumentRequest(@PathVariable Long id) {
        service.deleteDocumentRequest(id);
    }

}