package com.events.chalenge.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public record EventRecordDto(
    @NotBlank String name,
    @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
    @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate,
    @NotNull boolean active,
    @NotBlank String institutionId) {}
