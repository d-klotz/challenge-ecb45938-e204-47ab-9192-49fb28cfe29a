package com.events.chalenge.dtos;

import com.events.chalenge.enums.IntitutionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InstitutionRecordDto(@NotBlank String name, @NotNull IntitutionType type) {
}
