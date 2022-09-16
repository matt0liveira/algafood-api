package com.algafood.algafoodapi.domain.exceptions;

public class NegocioException extends RuntimeException {
    
    public NegocioException(String msg) {
        super(msg);
    }

    public NegocioException(String message, Throwable cause) {
        super(message, cause);
    }

}
