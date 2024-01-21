package com.events.chalenge.controllers;

import com.events.chalenge.dtos.EventRecordDto;
import com.events.chalenge.models.EventModel;
import com.events.chalenge.models.EventModel;
import com.events.chalenge.models.InstitutionModel;
import com.events.chalenge.repositories.EventRepository;
import com.events.chalenge.repositories.InstitutionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class EventController {

  @Autowired private EventRepository eventRepository;
  @Autowired private InstitutionRepository institutionRepository;

  @PostMapping("/events")
  public ResponseEntity<EventModel> createEvent(@RequestBody @Valid EventRecordDto eventRecordDto) {
    LocalDate startDate = LocalDate.parse(eventRecordDto.startDate());
    LocalDate endDate = LocalDate.parse(eventRecordDto.endDate());

    InstitutionModel institution =
        institutionRepository
            .findById(UUID.fromString(eventRecordDto.institutionId()))
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Institution not found with ID: " + eventRecordDto.institutionId()));

    EventModel eventModel = new EventModel();
    BeanUtils.copyProperties(eventRecordDto, eventModel);
    eventModel.setStartDate(startDate);
    eventModel.setEndDate(endDate);
    eventModel.setInstitution(institution);
    EventModel event = this.eventRepository.save(eventModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(event);
  }

  @GetMapping("/events/{id}")
  public ResponseEntity<EventModel> getEventById(@PathVariable UUID id) {
    Optional<EventModel> optionalEvent = eventRepository.findById(id);

    return optionalEvent
        .map(event -> ResponseEntity.ok().body(event))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/events")
  public ResponseEntity<List<EventModel>> getAllEvents() {
    List<EventModel> events = eventRepository.findAll();
    return ResponseEntity.ok().body(events);
  }

  @PutMapping("/events/{id}")
  public ResponseEntity<EventModel> updateEvent(
      @PathVariable UUID id, @RequestBody @Valid EventModel updatedEvent) {
    Optional<EventModel> optionalExistingEvent = eventRepository.findById(id);

    return optionalExistingEvent
        .map(
            existingEvent -> {
              BeanUtils.copyProperties(updatedEvent, existingEvent);
              EventModel savedEvent = eventRepository.save(existingEvent);
              return ResponseEntity.ok().body(savedEvent);
            })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/events/{id}")
  public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
    if (eventRepository.existsById(id)) {
      eventRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
