package com.events.chalenge.controllers;

import com.events.chalenge.dtos.EventRecordDto;
import com.events.chalenge.models.EventModel;
import com.events.chalenge.services.EventService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

  @Autowired private EventService eventService;

  @PostMapping("/events")
  public ResponseEntity<EventModel> createEvent(@RequestBody EventRecordDto eventDto) {
    EventModel createdEvent = eventService.createEvent(eventDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
  }

  @GetMapping("/events")
  public ResponseEntity<List<EventModel>> getAllEvents() {
    List<EventModel> events = eventService.getAllEvents();
    return ResponseEntity.ok().body(events);
  }

  @GetMapping("/events/{id}")
  public ResponseEntity<EventModel> getEventById(@PathVariable UUID id) {
    Optional<EventModel> optionalEvent = eventService.getEventById(id);

    return optionalEvent
        .map(event -> ResponseEntity.ok().body(event))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/events/{id}")
  public ResponseEntity<EventModel> updateEvent(
      @PathVariable UUID id, @RequestBody EventRecordDto eventDto) {
    Optional<EventModel> optionalUpdatedEvent = eventService.updateEvent(id, eventDto);

    return optionalUpdatedEvent
        .map(event -> ResponseEntity.ok().body(event))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/events/{id}")
  public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
    if (eventService.deleteEvent(id)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
