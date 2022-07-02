package com.algafood.algafoodapi.domain.exceptions;

public class RestauranteNotfoundException extends EntityNotfoundException {

    public RestauranteNotfoundException(String msg) {
        super(msg);
    }

    public RestauranteNotfoundException(Long restauranteId) {
        this(String.format("Restaurante de código %d não encontrada.", restauranteId));
    }
    
}
