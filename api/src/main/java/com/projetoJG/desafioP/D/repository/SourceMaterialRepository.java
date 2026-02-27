package com.projetoJG.desafioP.D.repository;

import com.projetoJG.desafioP.D.domain.entity.SourceMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceMaterialRepository extends JpaRepository<SourceMaterial, Long> {
}