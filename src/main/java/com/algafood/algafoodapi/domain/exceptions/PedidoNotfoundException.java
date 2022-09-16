package com.algafood.algafoodapi.domain.exceptions;

public class PedidoNotfoundException extends EntityNotfoundException {

    public PedidoNotfoundException(String msg) {
        super(msg);
    }

    public PedidoNotfoundException(Long pedidoId) {
        this(String.format("Pedido de código %d não encontrado!", pedidoId));
    }
    
}
