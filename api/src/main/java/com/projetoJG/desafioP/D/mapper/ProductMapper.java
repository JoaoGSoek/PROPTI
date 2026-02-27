package com.projetoJG.desafioP.D.mapper;

import com.projetoJG.desafioP.D.domain.dto.request.ProductRequestDTO;
import com.projetoJG.desafioP.D.domain.dto.response.ProductCompositionResponseDTO;
import com.projetoJG.desafioP.D.domain.dto.response.ProductResponseDTO;
import com.projetoJG.desafioP.D.domain.entity.Product;
import com.projetoJG.desafioP.D.domain.entity.ProductComposition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "composition", ignore = true) // Composition is handled in Service
    Product toEntity(ProductRequestDTO dto);

    ProductResponseDTO toResponse(Product entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "composition", ignore = true)
    void updateEntityFromDto(ProductRequestDTO dto, @MappingTarget Product entity);

    @Mapping(source = "sourceMaterial.id", target = "sourceMaterialId")
    @Mapping(source = "sourceMaterial.name", target = "sourceMaterialName")
    ProductCompositionResponseDTO toCompositionResponse(ProductComposition entity);
}