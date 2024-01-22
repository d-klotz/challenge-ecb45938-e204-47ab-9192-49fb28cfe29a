package com.events.chalenge.services;

import com.events.chalenge.models.EventModel;
import com.events.chalenge.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventSchedulerService {

  @Autowired private EventRepository eventRepository;

  // Depending on the use case, this method can be executed every night at midnight
  // or every minute, for example.
  // @Scheduled(cron = "0 0 0 * * ?", zone = "America/Sao_Paulo") // Executes every night at
  // midnight in Sao Paulo timezone
  @Scheduled(fixedRate = 30000) // Executes every 30 seconds
  public void updateEventStatus() {
    System.out.println("....................................");
    System.out.println("Starting event status update...");

    List<EventModel> nextOngoingEvents = this.fetchNextOngoingEvents();
    this.updateOngoingEvents(nextOngoingEvents);

    List<EventModel> eventsToBeDeactivated = fetchEventsToBeDeactivated();
    this.updateDeactivatedEvents(eventsToBeDeactivated);

    System.out.println("Deactivated: " + eventsToBeDeactivated.size() + " events");
    System.out.println("....................................");
  }

  private void updateDeactivatedEvents(List<EventModel> eventsToBeDeactivated) {
    for (EventModel event : eventsToBeDeactivated) {
      event.setActive(false);
      this.eventRepository.save(event);
    }
  }

  private List<EventModel> fetchEventsToBeDeactivated() {
    List<EventModel> eventsToBeDeactivated =
        this.eventRepository.findAllByEndDateBeforeAndActiveTrue(LocalDate.now());
    System.out.println("Fetched " + eventsToBeDeactivated.size() + " to be deactivated");
    return eventsToBeDeactivated;
  }

  private void updateOngoingEvents(List<EventModel> nextOngoingEvents) {
    for (EventModel event : nextOngoingEvents) {
      event.setActive(true);
      this.eventRepository.save(event);
    }
    System.out.println("Activated: " + nextOngoingEvents.size() + " events");
  }

  private List<EventModel> fetchNextOngoingEvents() {
    List<EventModel> nextOngoingEvents =
        this.eventRepository
            .findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndActiveFalse(
                LocalDate.now(), LocalDate.now());
    System.out.println("Fetched " + nextOngoingEvents.size() + " to be activated");
    return nextOngoingEvents;
  }
}
