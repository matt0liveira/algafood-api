package com.algafood.algafoodapi.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FormaPagamentoRefInput {

    @NotNull
    private Long id;

}
