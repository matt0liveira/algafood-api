package com.algafood.algafoodapi.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algafood.algafoodapi.api.v1.model.CozinhaModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "CozinhasModel")
@Setter
@Getter
public class CozinhasModelOpenApi {

    private CozinhaEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @Schema(name = "CozinhaModel")
    @Setter
    @Getter
    public class CozinhaEmbeddedModelOpenApi {
        List<CozinhaModel> cozinhas;
    }
}
