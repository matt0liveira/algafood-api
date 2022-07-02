package com.algafood.algafoodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algafood.algafoodapi.domain.models.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
        
}
