package com.events.chalenge.services;

import static org.mockito.Mockito.*;

import com.events.chalenge.models.EventModel;
import com.events.chalenge.repositories.EventRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EventSchedulerServiceTests {

  @Mock private EventRepository eventRepositoryMock;

  @InjectMocks private EventSchedulerService eventSchedulerService;

  @Test
  public void testUpdateEventStatus() {
    MockitoAnnotations.openMocks(this);

    EventModel ongoingEvent = new EventModel();
    ongoingEvent.setStartDate(LocalDate.now().minusDays(1));
    ongoingEvent.setEndDate(LocalDate.now().plusDays(1));
    ongoingEvent.setActive(false);

    EventModel eventToBeDeactivated = new EventModel();
    eventToBeDeactivated.setStartDate(LocalDate.now().minusDays(2));
    eventToBeDeactivated.setEndDate(LocalDate.now().minusDays(1));
    eventToBeDeactivated.setActive(true);

    List<EventModel> nextOngoingEvents = Arrays.asList(ongoingEvent);
    List<EventModel> eventsToBeDeactivated = Arrays.asList(eventToBeDeactivated);

    when(eventRepositoryMock
            .findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndActiveFalse(
                LocalDate.now(), LocalDate.now()))
        .thenReturn(nextOngoingEvents);

    when(eventRepositoryMock.findAllByEndDateBeforeAndActiveTrue(LocalDate.now()))
        .thenReturn(eventsToBeDeactivated);

    eventSchedulerService.updateEventStatus();

    verify(eventRepositoryMock, times(1)).save(ongoingEvent);
    verify(eventRepositoryMock, times(1)).save(eventToBeDeactivated);
  }
}
