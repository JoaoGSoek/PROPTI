package com.projetoJG.desafioP.D.controller;

import com.projetoJG.desafioP.D.domain.dto.request.SourceMaterialRequestDTO;
import com.projetoJG.desafioP.D.domain.dto.response.SourceMaterialResponseDTO;
import com.projetoJG.desafioP.D.service.SourceMaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/source-materials")
@RequiredArgsConstructor
public class SourceMaterialController {

    private final SourceMaterialService service;

    @PostMapping
    public ResponseEntity<SourceMaterialResponseDTO> create(@RequestBody @Valid SourceMaterialRequestDTO dto) {
        SourceMaterialResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<SourceMaterialResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SourceMaterialResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SourceMaterialResponseDTO> update(@PathVariable Long id, @RequestBody @Valid SourceMaterialRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}