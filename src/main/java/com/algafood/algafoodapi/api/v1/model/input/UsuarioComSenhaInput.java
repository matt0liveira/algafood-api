package com.algafood.algafoodapi.api.v1.model.input;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioComSenhaInput extends UsuarioInputModel {

    @NotBlank
    private String senha;

}
