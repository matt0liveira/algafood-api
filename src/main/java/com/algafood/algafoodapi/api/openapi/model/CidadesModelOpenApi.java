package com.algafood.algafoodapi.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algafood.algafoodapi.api.model.CidadeModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("CidadesModel")
public class CidadesModelOpenApi {

    private CidadeEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @Data
    @ApiModel("CidadesEmbeddedModel")
    public class CidadeEmbeddedModelOpenApi {
        private List<CidadeModel> cidades;
    }
}
