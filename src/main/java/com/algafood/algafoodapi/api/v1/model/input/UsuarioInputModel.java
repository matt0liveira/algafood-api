package com.algafood.algafoodapi.api.v1.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioInputModel {

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;
}
