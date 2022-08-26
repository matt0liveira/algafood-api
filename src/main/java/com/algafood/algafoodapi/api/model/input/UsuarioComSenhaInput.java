package com.algafood.algafoodapi.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioComSenhaInput extends UsuarioInputDTO {
    
    @NotBlank
    private String senha;

}
