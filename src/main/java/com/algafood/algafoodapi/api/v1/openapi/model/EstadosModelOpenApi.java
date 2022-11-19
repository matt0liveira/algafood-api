package com.algafood.algafoodapi.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algafood.algafoodapi.api.v1.model.EstadoModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "EstadosModel")
@Data
public class EstadosModelOpenApi {
    private EstadoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Schema(name = "EstadoModel")
    @Setter
    @Getter
    public class EstadoEmbeddedModelOpenApi {
        private List<EstadoModel> estados;
    }
}
