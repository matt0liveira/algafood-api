package com.algafood.algafoodapi.api.openapi.model;

import com.algafood.algafoodapi.api.model.CozinhaDTO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Setter
@Getter
public class CozinhasModelOpenApi extends PagedModelOpenApi<CozinhaDTO> {

}