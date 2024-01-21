package com.events.chalenge.controllers;

import com.events.chalenge.dtos.InstitutionRecordDto;
import com.events.chalenge.models.InstitutionModel;
import com.events.chalenge.repositories.InstitutionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class InstitutionController {

    @Autowired
    InstitutionRepository institutionRepository;
    @PostMapping("/institutions")
    public ResponseEntity<InstitutionModel> createInstitution(@RequestBody @Valid InstitutionRecordDto institutionRecordDto) {
        var institutionModel = new InstitutionModel();
        BeanUtils.copyProperties(institutionRecordDto, institutionModel);
        InstitutionModel institution = this.institutionRepository.save(institutionModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(institution);
    }

    @GetMapping("/institutions/{id}")
    public ResponseEntity<InstitutionModel> getInstitutionById(@PathVariable UUID id) {
        Optional<InstitutionModel> optionalInstitution = institutionRepository.findById(id);

        return optionalInstitution.map(institution -> ResponseEntity.ok().body(institution))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/institutions")
    public ResponseEntity<List<InstitutionModel>> getAllInstitutions() {
        List<InstitutionModel> institutions = institutionRepository.findAll();
        return ResponseEntity.ok().body(institutions);
    }

    @PutMapping("/institutions/{id}")
    public ResponseEntity<InstitutionModel> updateInstitution(
            @PathVariable UUID id,
            @RequestBody @Valid InstitutionRecordDto updatedInstitutionDto
    ) {
        Optional<InstitutionModel> optionalExistingInstitution = institutionRepository.findById(id);

        return optionalExistingInstitution.map(existingInstitution -> {
            BeanUtils.copyProperties(updatedInstitutionDto, existingInstitution);
            InstitutionModel updatedInstitution = institutionRepository.save(existingInstitution);
            return ResponseEntity.ok().body(updatedInstitution);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/institutions/{id}")
    public ResponseEntity<Void> deleteInstitution(@PathVariable UUID id) {
        if (institutionRepository.existsById(id)) {
            institutionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
