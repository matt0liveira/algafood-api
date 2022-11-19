package com.algafood.algafoodapi.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algafood.algafoodapi.api.v1.model.ProdutoModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "ProdutosModel")
@Data
public class ProdutosModelOpenApi {

    private ProdutoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Schema(name = "ProdutoModel")
    @Setter
    @Getter
    public class ProdutoEmbeddedModelOpenApi {
        private List<ProdutoModel> pedidos;
    }
}
