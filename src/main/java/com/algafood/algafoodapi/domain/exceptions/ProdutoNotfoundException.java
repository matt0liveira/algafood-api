package com.algafood.algafoodapi.domain.exceptions;

public class ProdutoNotfoundException extends EntityNotfoundException {

    public ProdutoNotfoundException(String msg) {
        super(msg);
    }

    public ProdutoNotfoundException(Long produtoId) {
        this(String.format("Produto de código %d não encontrado", produtoId));
    }
    
}
