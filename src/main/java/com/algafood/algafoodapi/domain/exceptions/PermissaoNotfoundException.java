package com.algafood.algafoodapi.domain.exceptions;

public class PermissaoNotfoundException extends EntityNotfoundException {

    public PermissaoNotfoundException(String msg) {
        super(msg);
    }

    public PermissaoNotfoundException(Long permissaoId) {
        this(String.format("Permissão de código %d não encontrada!", permissaoId));
    }
    
}
