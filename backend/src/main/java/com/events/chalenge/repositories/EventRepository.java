package com.events.chalenge.repositories;

import com.events.chalenge.models.EventModel;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventModel, UUID> {
  List<EventModel> findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndActiveFalse(
      LocalDate startDate, LocalDate endDate);

  List<EventModel> findAllByEndDateBeforeAndActiveTrue(LocalDate endDate);
}
