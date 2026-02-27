package com.projetoJG.desafioP.D.service;

import com.projetoJG.desafioP.D.domain.dto.request.SourceMaterialRequestDTO;
import com.projetoJG.desafioP.D.domain.dto.response.SourceMaterialResponseDTO;
import com.projetoJG.desafioP.D.domain.entity.SourceMaterial;
import com.projetoJG.desafioP.D.mapper.SourceMaterialMapper;
import com.projetoJG.desafioP.D.repository.SourceMaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SourceMaterialService {

    private final SourceMaterialRepository repository;
    private final SourceMaterialMapper mapper;

    @Transactional
    public SourceMaterialResponseDTO create(SourceMaterialRequestDTO dto) {
        SourceMaterial entity = mapper.toEntity(dto);
        SourceMaterial saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<SourceMaterialResponseDTO> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public SourceMaterialResponseDTO findById(Long id) {
        return repository.findById(id).map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("SourceMaterial not found with id: " + id));
    }

    @Transactional
    public SourceMaterialResponseDTO update(Long id, SourceMaterialRequestDTO dto) {
        SourceMaterial entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SourceMaterial not found with id: " + id));
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("SourceMaterial not found with id: " + id);
        }
        repository.deleteById(id);
    }
}