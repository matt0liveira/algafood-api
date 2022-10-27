package com.algafood.algafoodapi.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algafood.algafoodapi.api.model.PermissaoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@ApiModel("GruposPermissoesModel")
public class GrupoPermissoesModelOpenApi {

    private GrupoPermissoesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("GrupoPermissoesModel")
    @Setter
    @Getter
    public class GrupoPermissoesEmbeddedModelOpenApi {
        private List<PermissaoModel> permissoes;
    }

}
