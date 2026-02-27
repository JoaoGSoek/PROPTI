package com.projetoJG.desafioP.D.service;

import com.projetoJG.desafioP.D.domain.dto.request.ProductRequestDTO;
import com.projetoJG.desafioP.D.domain.dto.response.ProductSuggestionItemDTO;
import com.projetoJG.desafioP.D.domain.dto.response.ProductResponseDTO;
import com.projetoJG.desafioP.D.domain.dto.response.ProductionSuggestionResponseDTO;
import com.projetoJG.desafioP.D.domain.entity.Product;
import com.projetoJG.desafioP.D.domain.entity.ProductComposition;
import com.projetoJG.desafioP.D.domain.entity.SourceMaterial;
import com.projetoJG.desafioP.D.mapper.ProductMapper;
import com.projetoJG.desafioP.D.repository.ProductRepository;
import com.projetoJG.desafioP.D.repository.SourceMaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SourceMaterialRepository sourceMaterialRepository;
    private final ProductMapper mapper;

    @Transactional
    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product product = mapper.toEntity(dto);

        // Manually build composition to handle relationships and validation
        if (dto.composition() != null) {
            dto.composition().forEach(compDto -> {
                SourceMaterial material = sourceMaterialRepository.findById(compDto.sourceMaterialId())
                        .orElseThrow(() -> new EntityNotFoundException("SourceMaterial not found with id: " + compDto.sourceMaterialId()));

                ProductComposition composition = ProductComposition.builder()
                        .product(product)
                        .sourceMaterial(material)
                        .neededQuantity(compDto.neededQuantity())
                        .build();

                product.getComposition().add(composition);
            });
        }

        Product saved = productRepository.save(product);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id) {
        return productRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public ProductionSuggestionResponseDTO calculateProductionSuggestion() {
        List<Product> products = productRepository.findAll();
        List<SourceMaterial> materials = sourceMaterialRepository.findAll();

        // Map ID -> Available Quantity (Mutable for calculation)
        Map<Long, BigDecimal> stock = materials.stream()
                .collect(Collectors.toMap(SourceMaterial::getId, SourceMaterial::getAvailableQuantity));

        // Greedy Algorithm: Sort by Price Descending (Highest Return First)
        products.sort((p1, p2) -> p2.getPrice().compareTo(p1.getPrice()));

        List<ProductSuggestionItemDTO> suggestions = new ArrayList<>();
        BigDecimal totalValue = BigDecimal.ZERO;

        for (Product product : products) {
            if (product.getComposition().isEmpty()) continue;

            // Calculate max units we can produce with current stock
            int maxCanProduce = Integer.MAX_VALUE;

            for (ProductComposition comp : product.getComposition()) {
                BigDecimal available = stock.getOrDefault(comp.getSourceMaterial().getId(), BigDecimal.ZERO);
                BigDecimal needed = comp.getNeededQuantity();

                if (needed.compareTo(BigDecimal.ZERO) <= 0) continue;

                // Floor division to get whole units
                int possible = available.divide(needed, 0, RoundingMode.FLOOR).intValue();
                maxCanProduce = Math.min(maxCanProduce, possible);
            }

            if (maxCanProduce > 0 && maxCanProduce != Integer.MAX_VALUE) {
                // Deduct from stock
                for (ProductComposition comp : product.getComposition()) {
                    BigDecimal neededTotal = comp.getNeededQuantity().multiply(BigDecimal.valueOf(maxCanProduce));
                    stock.compute(comp.getSourceMaterial().getId(), (k, v) -> v.subtract(neededTotal));
                }

                BigDecimal subTotal = product.getPrice().multiply(BigDecimal.valueOf(maxCanProduce));
                suggestions.add(new ProductSuggestionItemDTO(product.getName(), maxCanProduce, subTotal));
                totalValue = totalValue.add(subTotal);
            }
        }

        return new ProductionSuggestionResponseDTO(suggestions, totalValue);
    }

    @Transactional
    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        mapper.updateEntityFromDto(dto, product);

        if (dto.composition() != null) {
            product.getComposition().clear();
            dto.composition().forEach(compDto -> {
                SourceMaterial material = sourceMaterialRepository.findById(compDto.sourceMaterialId())
                        .orElseThrow(() -> new EntityNotFoundException("SourceMaterial not found with id: " + compDto.sourceMaterialId()));

                ProductComposition composition = ProductComposition.builder()
                        .product(product)
                        .sourceMaterial(material)
                        .neededQuantity(compDto.neededQuantity())
                        .build();

                product.getComposition().add(composition);
            });
        }
        return mapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}