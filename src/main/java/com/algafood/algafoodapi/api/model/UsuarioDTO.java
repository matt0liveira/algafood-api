package com.algafood.algafoodapi.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioDTO {
    
    private Long id;
    private String nome;
    private String email;

}