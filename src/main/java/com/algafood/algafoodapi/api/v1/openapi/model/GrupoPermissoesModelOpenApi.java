package com.algafood.algafoodapi.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algafood.algafoodapi.api.v1.model.PermissaoModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Schema(name = "GruposPermissoesModel")
public class GrupoPermissoesModelOpenApi {

    private GrupoPermissoesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Schema(name = "GrupoPermissoesModel")
    @Setter
    @Getter
    public class GrupoPermissoesEmbeddedModelOpenApi {
        private List<PermissaoModel> permissoes;
    }

}
