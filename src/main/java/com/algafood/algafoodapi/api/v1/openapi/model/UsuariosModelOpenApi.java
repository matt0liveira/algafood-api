package com.algafood.algafoodapi.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algafood.algafoodapi.api.v1.model.UsuarioModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("UsuariosModel")
@Data
public class UsuariosModelOpenApi {

    private UsuarioEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("UsuarioModel")
    @Setter
    @Getter
    public class UsuarioEmbeddedModelOpenApi {
        private List<UsuarioModel> usuarios;
    }
}
