package com.algafood.algafoodapi.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algafood.algafoodapi.api.model.EstadoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("EstadosModel")
@Data
public class EstadosModelOpenApi {
    private EstadoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("EstadoModel")
    @Setter
    @Getter
    public class EstadoEmbeddedModelOpenApi {
        private List<EstadoModel> estados;
    }
}
