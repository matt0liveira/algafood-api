package com.algafood.algafoodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algafood.algafoodapi.domain.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
}
