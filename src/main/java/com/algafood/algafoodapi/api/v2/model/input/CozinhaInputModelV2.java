package com.algafood.algafoodapi.api.v2.model.input;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaInputModelV2 {

    @NotBlank
    private String nomeCozinha;

}
