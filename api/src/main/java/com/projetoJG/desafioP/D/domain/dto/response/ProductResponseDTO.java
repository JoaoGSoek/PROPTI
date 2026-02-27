package com.projetoJG.desafioP.D.domain.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponseDTO(
    Long id,
    String name,
    BigDecimal price,
    List<ProductCompositionResponseDTO> composition
) {}