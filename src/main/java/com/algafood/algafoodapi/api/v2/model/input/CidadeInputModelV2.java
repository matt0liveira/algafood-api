package com.algafood.algafoodapi.api.v2.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CidadeInputModel")
@Setter
@Getter
public class CidadeInputModelV2 {

    @NotBlank
    private String nomeCidade;

    @NotNull
    private Long idEstado;
}