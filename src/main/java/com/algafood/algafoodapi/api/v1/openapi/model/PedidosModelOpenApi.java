package com.algafood.algafoodapi.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algafood.algafoodapi.api.v1.model.PedidoResumoModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "PedidosModel")
@Setter
@Getter
public class PedidosModelOpenApi {

    private PedidosEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @Schema(name = "PedidoModel")
    @Setter
    @Getter
    public class PedidosEmbeddedModelOpenApi {
        private List<PedidoResumoModel> pedidos;
    }
}
