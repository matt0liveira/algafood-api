package com.algafood.algafoodapi.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algafood.algafoodapi.api.v1.model.GrupoModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Schema(name = "GruposModel")
public class GruposModelOpenApi {

    private GrupoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Setter
    @Getter
    @Schema(name = "GrupoModel")
    public class GrupoEmbeddedModelOpenApi {
        private List<GrupoModel> grupos;
    }
}
