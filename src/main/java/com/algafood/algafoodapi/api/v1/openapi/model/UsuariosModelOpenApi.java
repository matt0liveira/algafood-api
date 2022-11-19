package com.algafood.algafoodapi.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algafood.algafoodapi.api.v1.model.UsuarioModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "UsuariosModel")
@Data
public class UsuariosModelOpenApi {

    private UsuarioEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Schema(name = "UsuarioModel")
    @Setter
    @Getter
    public class UsuarioEmbeddedModelOpenApi {
        private List<UsuarioModel> usuarios;
    }
}
