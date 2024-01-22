package com.events.chalenge.controllers;

import com.events.chalenge.dtos.InstitutionRecordDto;
import com.events.chalenge.models.InstitutionModel;
import com.events.chalenge.services.InstitutionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class InstitutionController {

  private final InstitutionService institutionService;

  @Autowired
  public InstitutionController(InstitutionService institutionService) {
    this.institutionService = institutionService;
  }

  @PostMapping("/institutions")
  public ResponseEntity<InstitutionModel> createInstitution(
      @RequestBody @Valid InstitutionRecordDto institutionRecordDto) {
    InstitutionModel institution = institutionService.createInstitution(institutionRecordDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(institution);
  }

  @GetMapping("/institutions/{id}")
  public ResponseEntity<InstitutionModel> getInstitutionById(@PathVariable UUID id) {
    Optional<InstitutionModel> optionalInstitution = institutionService.getInstitutionById(id);

    return optionalInstitution
        .map(institution -> ResponseEntity.ok().body(institution))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/institutions")
  public ResponseEntity<List<InstitutionModel>> getAllInstitutions() {
    List<InstitutionModel> institutions = institutionService.getAllInstitutions();
    return ResponseEntity.ok().body(institutions);
  }

  @PutMapping("/institutions/{id}")
  public ResponseEntity<InstitutionModel> updateInstitution(
      @PathVariable UUID id, @RequestBody @Valid InstitutionRecordDto updatedInstitutionDto) {
    Optional<InstitutionModel> updatedInstitution =
        institutionService.updateInstitution(id, updatedInstitutionDto);

    return updatedInstitution
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/institutions/{id}")
  public ResponseEntity<Void> deleteInstitution(@PathVariable UUID id) {
    if (institutionService.deleteInstitution(id)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
