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

  // Depending on the dynamic of the events, this method can be executed every night at midnight
  // or every minute, for example.
  // @Scheduled(cron = "0 0 0 * * ?", zone = "America/Sao_Paulo") // Executes every night at
  // midnight in Sao Paulo timezone
  @Scheduled(fixedRate = 60000) // Execute every minute
  public void updateEventStatus() {
    System.out.println("....................................");
    System.out.println("Starting event status update...");

    List<EventModel> nextOngoingEvents =
        this.eventRepository
            .findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndActiveFalse(
                LocalDate.now(), LocalDate.now());
    System.out.println("Fetched " + nextOngoingEvents.size() + " to be activated");

    LocalDate currentDate = LocalDate.now();

    for (EventModel event : nextOngoingEvents) {
      event.setActive(true);
      this.eventRepository.save(event);
    }
    System.out.println("Activated: " + nextOngoingEvents.size() + " events");

    List<EventModel> eventsToBeDeactivated =
        this.eventRepository.findAllByEndDateBeforeAndActiveTrue(LocalDate.now());
    System.out.println("Fetched " + eventsToBeDeactivated.size() + " to be deactivated");

    for (EventModel event : eventsToBeDeactivated) {
      event.setActive(false);
      this.eventRepository.save(event);
    }

    System.out.println("Deactivated: " + eventsToBeDeactivated.size() + " events");

    System.out.println("....................................");
  }
}
