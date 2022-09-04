package com.algafood.algafoodapi.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algafood.algafoodapi.domain.exceptions.ProdutoNotfoundException;
import com.algafood.algafoodapi.domain.models.Produto;
import com.algafood.algafoodapi.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto findOrFail(Long restauranteId, Long produtoId) {
        return produtoRepository.findById(restauranteId, produtoId)
            .orElseThrow(() -> new ProdutoNotfoundException(produtoId));
    }
}
