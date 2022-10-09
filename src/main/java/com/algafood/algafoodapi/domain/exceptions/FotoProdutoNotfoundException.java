package com.algafood.algafoodapi.domain.exceptions;

public class FotoProdutoNotfoundException extends EntityNotfoundException {

    public FotoProdutoNotfoundException(String msg) {
        super(msg);
    }

    public FotoProdutoNotfoundException(Long produtoId, Long restauranteId) {
        this(String.format("Foto do produto de código %d do restaurante de código %d não encontrada.", produtoId, restauranteId));
    }
    
}
