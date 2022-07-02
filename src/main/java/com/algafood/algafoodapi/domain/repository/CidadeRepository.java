package com.algafood.algafoodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algafood.algafoodapi.domain.models.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {


}
