package com.projetoJG.desafioP.D.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import com.projetoJG.desafioP.D.domain.enums.MeasureType;
import java.math.BigDecimal;

@Entity
@Table(name = "source_material")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SourceMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal availableQuantity; // BigDecimal evita erro de arredondamento

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeasureType measureType;
}