package com.projetoJG.desafioP.D.repository;

import com.projetoJG.desafioP.D.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}