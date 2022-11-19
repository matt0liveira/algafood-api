package com.algafood.algafoodapi.api.v2.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algafood.algafoodapi.api.v2.model.CozinhaModelV2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Schema(name = "CozinhasModel")
public class CozinhasModelOpenApiV2 {

    private CozinhaEmbeddedModelOpenApiV2 _embedded;
    private Links _links;
    private PageModelOpenApiV2 page;

    @Setter
    @Getter
    @Schema(name = "CozinhaModel")
    public class CozinhaEmbeddedModelOpenApiV2 {
        private List<CozinhaModelV2> cozinhas;
    }
}
