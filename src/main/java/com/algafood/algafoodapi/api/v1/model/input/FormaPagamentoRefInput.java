package com.algafood.algafoodapi.api.v1.model.input;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FormaPagamentoRefInput {

    @NotNull
    private Long id;

}
