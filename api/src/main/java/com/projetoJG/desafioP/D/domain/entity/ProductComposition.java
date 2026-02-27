package com.projetoJG.desafioP.D.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product_composition")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductComposition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_material_id", nullable = false)
    private SourceMaterial sourceMaterial;

    @Column(nullable = false)
    private BigDecimal neededQuantity;
}