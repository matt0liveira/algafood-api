package com.algafood.algafoodapi.domain.exceptions;

public class EstadoNotfoundException extends EntityNotfoundException {

    public EstadoNotfoundException(String msg) {
        super(msg);
    }

    public EstadoNotfoundException(Long estadoId) {
        this(String.format("Estado de código %d não encontrado.", estadoId));
    }
    
}
