package com.projetoJG.desafioP.D.domain.dto.response;

import java.math.BigDecimal;

public record ProductSuggestionItemDTO(
    String productName,
    Integer quantity,
    BigDecimal totalValue
) {}