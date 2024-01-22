package com.events.chalenge.controllers;

import com.events.chalenge.dtos.InstitutionRecordDto;
import com.events.chalenge.models.InstitutionModel;
import com.events.chalenge.enums.IntitutionType;
import com.events.chalenge.services.InstitutionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InstitutionControllerTests {

  @Mock private InstitutionService institutionService;

  @InjectMocks private InstitutionController institutionController;

  public InstitutionControllerTests() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createInstitution() {
    InstitutionRecordDto recordDto = new InstitutionRecordDto("Banco BBC", IntitutionType.CENTRAL);
    InstitutionModel institutionModel = new InstitutionModel();

    when(institutionService.createInstitution(any())).thenReturn(institutionModel);

    ResponseEntity<InstitutionModel> response = institutionController.createInstitution(recordDto);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(institutionModel, response.getBody());

    verify(institutionService, times(1)).createInstitution(any());
  }

  @Test
  void getInstitutionById() {
    UUID id = UUID.randomUUID();
    InstitutionModel institutionModel = new InstitutionModel();

    when(institutionService.getInstitutionById(id)).thenReturn(Optional.of(institutionModel));

    ResponseEntity<InstitutionModel> response = institutionController.getInstitutionById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(institutionModel, response.getBody());

    verify(institutionService, times(1)).getInstitutionById(id);
  }

  @Test
  void getAllInstitutions() {
    List<InstitutionModel> institutions = Collections.singletonList(new InstitutionModel());

    when(institutionService.getAllInstitutions()).thenReturn(institutions);

    ResponseEntity<List<InstitutionModel>> response = institutionController.getAllInstitutions();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(institutions, response.getBody());

    verify(institutionService, times(1)).getAllInstitutions();
  }

  @Test
  void updateInstitution() {
    UUID id = UUID.randomUUID();
    InstitutionRecordDto recordDto = new InstitutionRecordDto("Banco BBC", IntitutionType.CENTRAL);
    InstitutionModel updatedInstitution = new InstitutionModel();

    when(institutionService.updateInstitution(id, recordDto))
        .thenReturn(Optional.of(updatedInstitution));

    ResponseEntity<InstitutionModel> response =
        institutionController.updateInstitution(id, recordDto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(updatedInstitution, response.getBody());

    verify(institutionService, times(1)).updateInstitution(id, recordDto);
  }

  @Test
  void deleteInstitution() {
    UUID id = UUID.randomUUID();

    when(institutionService.deleteInstitution(id)).thenReturn(true);

    ResponseEntity<Void> response = institutionController.deleteInstitution(id);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    verify(institutionService, times(1)).deleteInstitution(id);
  }
}
