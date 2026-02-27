package com.projetoJG.desafioP.D.domain.dto.request;

import com.projetoJG.desafioP.D.domain.enums.MeasureType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record SourceMaterialRequestDTO(
    @NotBlank(message = "Name is required") String name,
    @NotNull(message = "Quantity is required") @PositiveOrZero BigDecimal availableQuantity,
    @NotNull(message = "Measure type is required") MeasureType measureType
) {}