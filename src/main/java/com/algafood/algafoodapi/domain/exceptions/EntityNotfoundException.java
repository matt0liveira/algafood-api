package com.algafood.algafoodapi.domain.exceptions;

public abstract class EntityNotfoundException extends NegocioException {
    
    public EntityNotfoundException(String msg) {
        super(msg);
    }

}
