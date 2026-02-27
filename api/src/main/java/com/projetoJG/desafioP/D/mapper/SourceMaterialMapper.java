package com.projetoJG.desafioP.D.mapper;

import com.projetoJG.desafioP.D.domain.dto.request.SourceMaterialRequestDTO;
import com.projetoJG.desafioP.D.domain.dto.response.SourceMaterialResponseDTO;
import com.projetoJG.desafioP.D.domain.entity.SourceMaterial;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SourceMaterialMapper {
    
    SourceMaterial toEntity(SourceMaterialRequestDTO dto);
    SourceMaterialResponseDTO toResponse(SourceMaterial entity);
    void updateEntityFromDto(SourceMaterialRequestDTO dto, @MappingTarget SourceMaterial entity);
}