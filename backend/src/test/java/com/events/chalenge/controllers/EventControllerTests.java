package com.events.chalenge.controllers;

import com.events.chalenge.dtos.EventRecordDto;
import com.events.chalenge.models.EventModel;
import com.events.chalenge.services.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EventControllerTests {

  @Mock private EventService eventService;

  @InjectMocks private EventController eventController;

  public EventControllerTests() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createEvent() {
    EventRecordDto recordDto =
        new EventRecordDto("New event", LocalDate.now(), LocalDate.now(), "1");
    EventModel createdEvent = new EventModel();

    when(eventService.createEvent(any())).thenReturn(createdEvent);

    ResponseEntity<EventModel> response = eventController.createEvent(recordDto);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(createdEvent, response.getBody());

    verify(eventService, times(1)).createEvent(any());
  }

  @Test
  void getAllEvents() {
    List<EventModel> events = Collections.singletonList(new EventModel());

    when(eventService.getAllEvents()).thenReturn(events);

    ResponseEntity<List<EventModel>> response = eventController.getAllEvents();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(events, response.getBody());

    verify(eventService, times(1)).getAllEvents();
  }

  @Test
  void getEventById() {
    UUID id = UUID.randomUUID();
    EventModel eventModel = new EventModel();

    when(eventService.getEventById(id)).thenReturn(Optional.of(eventModel));

    ResponseEntity<EventModel> response = eventController.getEventById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(eventModel, response.getBody());

    verify(eventService, times(1)).getEventById(id);
  }

  @Test
  void updateEvent() {
    UUID id = UUID.randomUUID();
    EventRecordDto recordDto =
        new EventRecordDto("New event", LocalDate.now(), LocalDate.now(), "1");
    EventModel updatedEvent = new EventModel();

    when(eventService.updateEvent(id, recordDto)).thenReturn(Optional.of(updatedEvent));

    ResponseEntity<EventModel> response = eventController.updateEvent(id, recordDto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(updatedEvent, response.getBody());

    verify(eventService, times(1)).updateEvent(id, recordDto);
  }

  @Test
  void deleteEvent() {
    UUID id = UUID.randomUUID();

    when(eventService.deleteEvent(id)).thenReturn(true);

    ResponseEntity<Void> response = eventController.deleteEvent(id);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    verify(eventService, times(1)).deleteEvent(id);
  }
}
