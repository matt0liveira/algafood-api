package com.algafood.algafoodapi.domain.exceptions;

public class FormaPagamentoNotfoundException extends EntityNotfoundException {

    public FormaPagamentoNotfoundException(String msg) {
        super(msg);
    }

    public FormaPagamentoNotfoundException(Long formaPagamentoId) {
        this(String.format("Forma de pagamento de código %s não encontrada.", formaPagamentoId));
    }
    
}
