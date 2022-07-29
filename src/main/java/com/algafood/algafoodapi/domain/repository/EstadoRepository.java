package com.algafood.algafoodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algafood.algafoodapi.domain.models.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
        
}
