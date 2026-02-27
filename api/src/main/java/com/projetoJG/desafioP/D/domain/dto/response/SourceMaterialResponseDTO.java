package com.projetoJG.desafioP.D.domain.dto.response;

import com.projetoJG.desafioP.D.domain.enums.MeasureType;
import java.math.BigDecimal;

public record SourceMaterialResponseDTO(
    Long id,
    String name,
    BigDecimal availableQuantity,
    MeasureType measureType
) {}