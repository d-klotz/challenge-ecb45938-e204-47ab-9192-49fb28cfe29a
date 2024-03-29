package com.events.chalenge.models;

import com.events.chalenge.enums.IntitutionType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "INSTITUTIONS")
public class InstitutionModel implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String name;

  @Enumerated(EnumType.STRING)
  private IntitutionType type;

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

  public IntitutionType getType() {
    return type;
  }

  public void setType(IntitutionType type) {
    this.type = type;
  }
}
