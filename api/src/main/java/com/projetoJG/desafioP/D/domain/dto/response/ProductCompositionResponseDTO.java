package com.projetoJG.desafioP.D.domain.dto.response;

import java.math.BigDecimal;

public record ProductCompositionResponseDTO(
    Long id,
    Long sourceMaterialId,
    String sourceMaterialName,
    BigDecimal neededQuantity
) {}