package com.algafood.algafoodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algafood.algafoodapi.domain.models.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    
}
