package com.algafood.algafoodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algafood.algafoodapi.domain.models.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
    
}
