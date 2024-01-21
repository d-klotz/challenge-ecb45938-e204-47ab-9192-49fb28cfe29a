package com.events.chalenge.repositories;

import com.events.chalenge.models.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<EventModel, UUID> {
}
