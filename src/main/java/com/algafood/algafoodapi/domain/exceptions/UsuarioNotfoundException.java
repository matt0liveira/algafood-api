package com.algafood.algafoodapi.domain.exceptions;

public class UsuarioNotfoundException extends EntityNotfoundException {

    public UsuarioNotfoundException(String msg) {
        super(msg);
    }

    public UsuarioNotfoundException(Long usuarioId) {
        this(String.format("Usuário de código %d não encontrado.", usuarioId));
    }
    
}
