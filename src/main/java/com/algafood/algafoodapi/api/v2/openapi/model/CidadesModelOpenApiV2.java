package com.algafood.algafoodapi.api.v2.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algafood.algafoodapi.api.v1.openapi.model.CidadesModelOpenApi.CidadeEmbeddedModelOpenApi;
import com.algafood.algafoodapi.api.v2.model.CidadeModelV2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Schema(name = "CidadesModel")
public class CidadesModelOpenApiV2 {

    private CidadeEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Setter
    @Getter
    @Schema(name = "CidadeModel")
    public class CidadeEmbeddedModelOpenApiV2 {
        private List<CidadeModelV2> cidades;
    }
}
