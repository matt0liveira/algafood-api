package com.algafood.algafoodapi.api.v1.model.input;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteRefInput {

    @NotNull
    private Long id;

}
