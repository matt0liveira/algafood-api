package com.algafood.algafoodapi.api.v1.model.input;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoRefInput {

    @NotNull
    private Long id;

}
