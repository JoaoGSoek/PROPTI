package com.projetoJG.desafioP.D.domain.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record ProductionSuggestionResponseDTO(
    List<ProductSuggestionItemDTO> products,
    BigDecimal totalEstimatedValue
) {}