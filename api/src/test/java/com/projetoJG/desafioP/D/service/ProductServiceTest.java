package com.projetoJG.desafioP.D.service;

import com.projetoJG.desafioP.D.domain.dto.response.ProductSuggestionItemDTO;
import com.projetoJG.desafioP.D.domain.dto.response.ProductionSuggestionResponseDTO;
import com.projetoJG.desafioP.D.domain.entity.Product;
import com.projetoJG.desafioP.D.domain.entity.ProductComposition;
import com.projetoJG.desafioP.D.domain.entity.SourceMaterial;
import com.projetoJG.desafioP.D.mapper.ProductMapper;
import com.projetoJG.desafioP.D.repository.ProductRepository;
import com.projetoJG.desafioP.D.repository.SourceMaterialRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SourceMaterialRepository sourceMaterialRepository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Should prioritize the most expensive product when stock is limited to 1 unit")
    void calculateProductionSuggestion_ShouldPrioritizeMostExpensive_WhenStockIsLimited() {
        // Arrange
        SourceMaterial wood = SourceMaterial.builder()
                .id(1L)
                .name("Wood")
                .availableQuantity(new BigDecimal("10"))
                .build();

        // Product 1: Expensive (100.00), Needs 10 Wood
        Product luxuryChair = Product.builder()
                .id(1L)
                .name("Luxury Chair")
                .price(new BigDecimal("100"))
                .composition(new ArrayList<>())
                .build();
        luxuryChair.getComposition().add(ProductComposition.builder()
                .product(luxuryChair)
                .sourceMaterial(wood)
                .neededQuantity(new BigDecimal("10"))
                .build());

        // Product 2: Cheap (20.00), Needs 5 Wood
        Product stool = Product.builder()
                .id(2L)
                .name("Stool")
                .price(new BigDecimal("20"))
                .composition(new ArrayList<>())
                .build();
        stool.getComposition().add(ProductComposition.builder()
                .product(stool)
                .sourceMaterial(wood)
                .neededQuantity(new BigDecimal("5"))
                .build());

        when(sourceMaterialRepository.findAll()).thenReturn(List.of(wood));
        // Must return a mutable list because the service sorts it
        when(productRepository.findAll()).thenReturn(new ArrayList<>(List.of(stool, luxuryChair)));

        // Act
        ProductionSuggestionResponseDTO result = productService.calculateProductionSuggestion();

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal("100"), result.totalEstimatedValue());
        assertEquals(1, result.products().size());
        
        ProductSuggestionItemDTO item = result.products().getFirst();
        assertEquals("Luxury Chair", item.productName());
        assertEquals(1, item.quantity());
    }

    @Test
    @DisplayName("Should return empty suggestion when stock is zero")
    void calculateProductionSuggestion_ShouldReturnEmpty_WhenStockIsZero() {
        // Arrange
        SourceMaterial wood = SourceMaterial.builder().id(1L).availableQuantity(BigDecimal.ZERO).build();
        
        Product chair = Product.builder().id(1L).price(new BigDecimal("50")).composition(new ArrayList<>()).build();
        chair.getComposition().add(ProductComposition.builder()
                .product(chair).sourceMaterial(wood).neededQuantity(new BigDecimal("5")).build());

        when(sourceMaterialRepository.findAll()).thenReturn(List.of(wood));
        when(productRepository.findAll()).thenReturn(new ArrayList<>(List.of(chair)));

        // Act
        ProductionSuggestionResponseDTO result = productService.calculateProductionSuggestion();

        // Assert
        assertEquals(BigDecimal.ZERO, result.totalEstimatedValue());
        assertTrue(result.products().isEmpty());
    }

    @Test
    @DisplayName("Should choose the most profitable product when competing for the same material")
    void calculateProductionSuggestion_ShouldChooseMostProfitable_WhenCompetingForSameMaterial() {
        // Arrange
        SourceMaterial gold = SourceMaterial.builder().id(1L).availableQuantity(new BigDecimal("10")).build();

        Product ring = Product.builder().id(1L).name("Ring").price(new BigDecimal("200")).composition(new ArrayList<>()).build();
        ring.getComposition().add(ProductComposition.builder().product(ring).sourceMaterial(gold).neededQuantity(new BigDecimal("10")).build());

        Product chain = Product.builder().id(2L).name("Chain").price(new BigDecimal("150")).composition(new ArrayList<>()).build();
        chain.getComposition().add(ProductComposition.builder().product(chain).sourceMaterial(gold).neededQuantity(new BigDecimal("10")).build());

        when(sourceMaterialRepository.findAll()).thenReturn(List.of(gold));
        when(productRepository.findAll()).thenReturn(new ArrayList<>(List.of(chain, ring)));

        // Act
        ProductionSuggestionResponseDTO result = productService.calculateProductionSuggestion();

        // Assert
        assertEquals(new BigDecimal("200"), result.totalEstimatedValue());
        assertEquals("Ring", result.products().getFirst().productName());
    }
}