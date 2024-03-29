package com.events.chalenge.services;

import com.events.chalenge.dtos.EventRecordDto;
import com.events.chalenge.models.EventModel;
import com.events.chalenge.models.InstitutionModel;
import com.events.chalenge.repositories.EventRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

  @Autowired private EventRepository eventRepository;

  @Autowired private InstitutionService institutionService;

  public EventModel createEvent(EventRecordDto eventRecordDto) {

    if (isEndDayBeforeStartDay(eventRecordDto)) {
      throw new IllegalArgumentException("End date cannot be before start date.");
    }

    InstitutionModel institution =
        this.institutionService
            .getInstitutionById(UUID.fromString(eventRecordDto.institutionId()))
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Institution not found with ID: " + eventRecordDto.institutionId()));

    EventModel eventModel = new EventModel();
    BeanUtils.copyProperties(eventRecordDto, eventModel);
    eventModel.setActive(this.isEventStartingToday(eventRecordDto));
    eventModel.setInstitution(institution);
    return eventRepository.save(eventModel);
  }

  public List<EventModel> getAllEvents() {
    return eventRepository.findAll();
  }

  public Optional<EventModel> getEventById(UUID id) {
    return eventRepository.findById(id);
  }

  public Optional<EventModel> updateEvent(UUID id, EventRecordDto eventRecordDto) {
    Optional<EventModel> optionalExistingEvent = eventRepository.findById(id);

    InstitutionModel institution =
        this.institutionService
            .getInstitutionById(UUID.fromString(eventRecordDto.institutionId()))
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Institution not found with ID: " + eventRecordDto.institutionId()));

    return optionalExistingEvent.map(
        existingEvent -> {
          BeanUtils.copyProperties(eventRecordDto, existingEvent);
          existingEvent.setInstitution(institution);
          return eventRepository.save(existingEvent);
        });
  }

  public boolean deleteEvent(UUID id) {
    if (eventRepository.existsById(id)) {
      eventRepository.deleteById(id);
      return true;
    } else {
      return false;
    }
  }

  private boolean isEventStartingToday(EventRecordDto eventRecordDto) {
    return eventRecordDto.startDate().isEqual(LocalDate.now());
  }

  private boolean isStartDayAfterEndDay(EventRecordDto eventRecordDto) {
    return eventRecordDto.startDate().isAfter(eventRecordDto.endDate());
  }

  private boolean isEndDayBeforeStartDay(EventRecordDto eventRecordDto) {
    return eventRecordDto.endDate().isBefore(eventRecordDto.startDate());
  }
}
