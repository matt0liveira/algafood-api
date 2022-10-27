package com.algafood.algafoodapi.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algafood.algafoodapi.api.model.PedidoResumoModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PedidosModel")
@Setter
@Getter
public class PedidosModelOpenApi {

    private PedidosEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @ApiModel("PedidoModel")
    @Setter
    @Getter
    public class PedidosEmbeddedModelOpenApi {
        private List<PedidoResumoModel> pedidos;
    }
}
