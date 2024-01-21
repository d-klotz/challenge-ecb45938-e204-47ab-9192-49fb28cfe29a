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

    @Autowired
    private EventRepository eventRepository;

    //Depending on the dynamic of the events, this method can be executed every night at midnight
    //or every minute, for example.
    // @Scheduled(cron = "0 0 0 * * ?", zone = "America/Sao_Paulo") // Executes every night at midnight in Sao Paulo timezone
    @Scheduled(fixedRate = 60000) // Execute every minute
    public void updateEventStatus() {
        System.out.println("....................................");
        System.out.println("Starting event status update...");

        //todo: fetch only events that are not in the past, as they will never change its status back to active
        List<EventModel> events = this.eventRepository.findAll();
        System.out.println("Fetched " + events.size() + " events");

        LocalDate currentDate = LocalDate.now();
        var amountOfUpdatedEvents = 0;

        for (EventModel event : events) {
            if (currentDate.isEqual(event.getStartDate()) && !event.isActive()) {
                event.setActive(true);
                amountOfUpdatedEvents += 1 ;
                this.eventRepository.save(event);
            } else if (currentDate.isAfter(event.getEndDate()) && event.isActive()) {
                event.setActive(false);
                amountOfUpdatedEvents+= 1;
                this.eventRepository.save(event);
            }
        }

        System.out.println("Amount of updated events: " + amountOfUpdatedEvents);
        System.out.println("....................................");
    }
}
