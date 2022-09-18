package com.algafood.algafoodapi.domain.exceptions;

public class PedidoNotfoundException extends EntityNotfoundException {

    public PedidoNotfoundException(String codigoPedido) {
        super(String.format("Pedido de código %s não encontrado!", codigoPedido));
    }
    
}
