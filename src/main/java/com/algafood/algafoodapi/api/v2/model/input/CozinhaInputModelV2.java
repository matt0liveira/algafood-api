package com.algafood.algafoodapi.api.v2.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhaInputModel")
@Getter
@Setter
public class CozinhaInputModelV2 {

    @NotBlank
    private String nomeCozinha;

}
