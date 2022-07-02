package com.algafood.algafoodapi.domain.repository;

import java.util.List;

import com.algafood.algafoodapi.domain.models.FormaPagamento;

public interface FormaPagamentoRepository {

    List<FormaPagamento> listar();
    FormaPagamento buscar(Long id);
    void salvar(FormaPagamento formaPagamento);
    void remover(FormaPagamento formaPagamento);
    
}
