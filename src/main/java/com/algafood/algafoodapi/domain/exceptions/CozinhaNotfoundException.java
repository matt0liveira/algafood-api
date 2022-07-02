package com.algafood.algafoodapi.domain.exceptions;

public class CozinhaNotfoundException extends EntityNotfoundException {

    public CozinhaNotfoundException(String msg) {
        super(msg);
    }

    public CozinhaNotfoundException(Long cozinhaId) {
        this(String.format("Cozinha de código %d não encontrada.", cozinhaId));
    }
    
}
