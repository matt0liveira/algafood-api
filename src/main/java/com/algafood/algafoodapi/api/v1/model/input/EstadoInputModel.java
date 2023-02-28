package com.algafood.algafoodapi.api.v1.model.input;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoInputModel {

    @NotBlank
    private String nome;
}