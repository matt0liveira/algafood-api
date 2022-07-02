package com.algafood.algafoodapi.domain.exceptions;

public class CidadeNotfoundException extends EntityNotfoundException {

    public CidadeNotfoundException(String msg) {
        super(msg);
    }

    public CidadeNotfoundException(Long cidadeId) {
        this(String.format("Cidade de código %d não encontrada.", cidadeId));
    }
    
}
