package com.algafood.algafoodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algafood.algafoodapi.domain.models.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

}
