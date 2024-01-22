package com.events.chalenge.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.events.chalenge.dtos.EventRecordDto;
import com.events.chalenge.models.EventModel;
import com.events.chalenge.models.InstitutionModel;
import com.events.chalenge.repositories.EventRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class EventServiceTests {

  @Mock private EventRepository eventRepository;

  @Mock private InstitutionService institutionService;

  @InjectMocks private EventService eventService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createEvent() {
    EventRecordDto eventRecordDto =
        new EventRecordDto(
            "Test Event", LocalDate.now(), LocalDate.now(), UUID.randomUUID().toString());

    InstitutionModel institutionModel = new InstitutionModel();
    institutionModel.setId(UUID.fromString(eventRecordDto.institutionId()));

    when(institutionService.getInstitutionById(any())).thenReturn(Optional.of(institutionModel));
    when(eventRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    EventModel createdEvent = eventService.createEvent(eventRecordDto);

    assertNotNull(createdEvent);
    assertEquals(eventRecordDto.name(), createdEvent.getName());
    assertTrue(createdEvent.isActive());

    verify(institutionService, times(1))
        .getInstitutionById(UUID.fromString(eventRecordDto.institutionId()));
    verify(eventRepository, times(1)).save(any());
  }

  @Test
  void createEventWithStarDateAfterEndDate() {
    EventRecordDto eventRecordDto =
        new EventRecordDto(
            "Test Event",
            LocalDate.now().plusDays(10),
            LocalDate.now(),
            UUID.randomUUID().toString());

    IllegalArgumentException thrownException =
        assertThrows(
            IllegalArgumentException.class, () -> eventService.createEvent(eventRecordDto));
    assertEquals("End date cannot be before start date.", thrownException.getMessage());
  }

  @Test
  void createEventWithEndDateBeforeStartDate() {
    EventRecordDto eventRecordDto =
        new EventRecordDto(
            "Test Event",
            LocalDate.now(),
            LocalDate.now().minusDays(10),
            UUID.randomUUID().toString());

    IllegalArgumentException thrownException =
        assertThrows(
            IllegalArgumentException.class, () -> eventService.createEvent(eventRecordDto));
    assertEquals("End date cannot be before start date.", thrownException.getMessage());
  }

  @Test
  void getAllEvents() {
    when(eventRepository.findAll()).thenReturn(List.of(new EventModel(), new EventModel()));

    List<EventModel> events = eventService.getAllEvents();

    assertEquals(2, events.size());

    verify(eventRepository, times(1)).findAll();
  }

  @Test
  void getEventById() {
    UUID eventId = UUID.randomUUID();
    when(eventRepository.findById(eventId)).thenReturn(Optional.of(new EventModel()));

    Optional<EventModel> event = eventService.getEventById(eventId);

    assertTrue(event.isPresent());

    verify(eventRepository, times(1)).findById(eventId);
  }

  @Test
  void updateEvent() {
    UUID eventId = UUID.randomUUID();
    InstitutionModel institutionModel = new InstitutionModel();
    institutionModel.setId(UUID.randomUUID());
    EventRecordDto eventRecordDto =
        new EventRecordDto(
            "Test Event", LocalDate.now(), LocalDate.now(), institutionModel.getId().toString());

    when(institutionService.getInstitutionById(any())).thenReturn(Optional.of(institutionModel));
    when(eventRepository.findById(eventId)).thenReturn(Optional.of(new EventModel()));
    when(eventRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    Optional<EventModel> updatedEvent = eventService.updateEvent(eventId, eventRecordDto);

    assertTrue(updatedEvent.isPresent());
    assertEquals(eventRecordDto.name(), updatedEvent.get().getName());

    verify(institutionService, times(1)).getInstitutionById(institutionModel.getId());
    verify(eventRepository, times(1)).findById(eventId);
    verify(eventRepository, times(1)).save(any());
  }

  @Test
  void deleteEvent() {
    UUID eventId = UUID.randomUUID();

    when(eventRepository.existsById(eventId)).thenReturn(true);

    boolean result = eventService.deleteEvent(eventId);

    assertTrue(result);

    verify(eventRepository, times(1)).existsById(eventId);
    verify(eventRepository, times(1)).deleteById(eventId);
  }

  @Test
  void deleteEvent_NotFound() {
    UUID eventId = UUID.randomUUID();

    when(eventRepository.existsById(eventId)).thenReturn(false);

    boolean result = eventService.deleteEvent(eventId);

    assertFalse(result);

    verify(eventRepository, times(1)).existsById(eventId);
    verify(eventRepository, never()).deleteById(any());
  }
}
