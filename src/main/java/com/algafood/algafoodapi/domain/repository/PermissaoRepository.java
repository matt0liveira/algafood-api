package com.algafood.algafoodapi.domain.repository;

import java.util.List;

import com.algafood.algafoodapi.domain.models.Permissao;

public interface PermissaoRepository {
    List<Permissao> listar();
    Permissao buscar(Long id);
    void salvar(Permissao permissao);
    void remover(Permissao permissao);
}
