package com.projetoJG.desafioP.D.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ProductCompositionRequestDTO(
    @NotNull(message = "Source Material ID is required") Long sourceMaterialId,
    @NotNull(message = "Needed quantity is required") @Positive BigDecimal neededQuantity
) {}