package com.events.chalenge.repositories;

import com.events.chalenge.models.InstitutionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InstitutionRepository extends JpaRepository<InstitutionModel, UUID> {
}
