package com.algafood.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algafood.algafoodapi.domain.models.FotoProduto;
import com.algafood.algafoodapi.domain.repository.ProdutoRepository;

@Service
public class CatalogoFotoProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    public FotoProduto salvar(FotoProduto foto) {
        return produtoRepository.save(foto);
    }

}
