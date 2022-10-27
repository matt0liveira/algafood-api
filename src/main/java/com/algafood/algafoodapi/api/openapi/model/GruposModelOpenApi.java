package com.algafood.algafoodapi.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algafood.algafoodapi.api.model.GrupoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@ApiModel("GruposModel")
public class GruposModelOpenApi {

    private GrupoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Setter
    @Getter
    @ApiModel("GrupoModel")
    public class GrupoEmbeddedModelOpenApi {
        private List<GrupoModel> grupos;
    }
}
