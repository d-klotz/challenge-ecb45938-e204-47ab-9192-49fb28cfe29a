package com.events.chalenge.services;

import com.events.chalenge.dtos.InstitutionRecordDto;
import com.events.chalenge.models.InstitutionModel;
import com.events.chalenge.repositories.InstitutionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InstitutionService {

  private final InstitutionRepository institutionRepository;

  @Autowired
  public InstitutionService(InstitutionRepository institutionRepository) {
    this.institutionRepository = institutionRepository;
  }

  public InstitutionModel createInstitution(InstitutionRecordDto institutionRecordDto) {
    InstitutionModel institutionModel = new InstitutionModel();
    BeanUtils.copyProperties(institutionRecordDto, institutionModel);
    return this.institutionRepository.save(institutionModel);
  }

  public Optional<InstitutionModel> getInstitutionById(UUID id) {
    return this.institutionRepository.findById(id);
  }

  public List<InstitutionModel> getAllInstitutions() {
    return this.institutionRepository.findAll();
  }

  public Optional<InstitutionModel> updateInstitution(
      UUID id, InstitutionRecordDto updatedInstitutionDto) {
    Optional<InstitutionModel> optionalExistingInstitution =
        this.institutionRepository.findById(id);

    return optionalExistingInstitution
        .map(
            existingInstitution -> {
              BeanUtils.copyProperties(updatedInstitutionDto, existingInstitution);
              InstitutionModel updatedInstitution =
                  this.institutionRepository.save(existingInstitution);
              return Optional.of(updatedInstitution);
            })
        .orElse(Optional.empty());
  }

  public boolean deleteInstitution(UUID id) {
    if (this.institutionRepository.existsById(id)) {
      this.institutionRepository.deleteById(id);
      return true;
    } else {
      return false;
    }
  }
}
