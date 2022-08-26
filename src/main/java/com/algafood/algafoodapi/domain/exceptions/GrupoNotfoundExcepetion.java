package com.algafood.algafoodapi.domain.exceptions;

public class GrupoNotfoundExcepetion extends EntityNotfoundException {

    public GrupoNotfoundExcepetion(String msg) {
        super(msg);
        //TODO Auto-generated constructor stub
    }

    public GrupoNotfoundExcepetion(Long grupoId) {
        this(String.format("Grupo de código %d não encontrado.", grupoId));
    }
    
}
