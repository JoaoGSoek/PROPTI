package com.projetoJG.desafioP.D.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

public record ProductRequestDTO(
    @NotBlank(message = "Name is required") String name,
    @NotNull(message = "Price is required") @PositiveOrZero BigDecimal price,
    @NotEmpty(message = "Composition list cannot be empty") List<ProductCompositionRequestDTO> composition
) {}