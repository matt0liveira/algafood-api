package com.algafood.algafoodapi.domain.exceptions;

public class EntityInUseException extends RuntimeException {
    public EntityInUseException(String msg) {
        super(msg);
    }
}
