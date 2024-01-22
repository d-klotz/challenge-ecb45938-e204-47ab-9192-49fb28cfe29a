package com.events.chalenge.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "EVENTS")
public class EventModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @NotBlank(message = "Event name cannot be empty or null.")
  private String name;

  @NotNull(message = "Start date cannot be null.")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @NotNull(message = "End date cannot be null.")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  private boolean active;

  @ManyToOne
  @JoinColumn(name = "institution_id")
  private InstitutionModel institution;

  @AssertTrue(message = "Start date must be before the end date.")
  private boolean isValidDateRange() {
    return startDate == null || endDate == null || !startDate.isAfter(endDate);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public InstitutionModel getInstitution() {
    return institution;
  }

  public void setInstitution(InstitutionModel institution) {
    this.institution = institution;
  }
}
