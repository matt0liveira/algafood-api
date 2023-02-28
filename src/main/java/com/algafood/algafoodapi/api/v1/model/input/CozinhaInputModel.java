package com.algafood.algafoodapi.api.v1.model.input;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaInputModel {

    @NotBlank
    private String nome;

}
