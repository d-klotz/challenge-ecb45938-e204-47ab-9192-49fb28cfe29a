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
        
        List<EventModel> events = this.eventRepository.findAll();
        LocalDate currentDate = LocalDate.now();
        var amountOfUpdatedEvents = 0;

        for (EventModel event : events) {
            if (currentDate.isEqual(event.getStartDate()) || currentDate.isAfter(event.getStartDate())) {
                event.setActive(true);
                amountOfUpdatedEvents++;
            } else if (currentDate.isAfter(event.getEndDate())) {
                event.setActive(false);
                amountOfUpdatedEvents++;
            }

            this.eventRepository.save(event);
        }

        System.out.println("Amount of updated events: " + amountOfUpdatedEvents);
        System.out.println("....................................");
    }
}
