package com.algafood.algafoodapi.api.model;

import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

// import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Getter;
import lombok.Setter;

// @JsonFilter("pedidoFilter")
@Relation(collectionRelation = "pedidos")
@Setter
@Getter
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel> {

    private String codigo;
    private Long subtotal;
    private Long taxaFrete;
    private Long valorTotal;
    private ItemPedidoModel itemPedido;
    private String status;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;
    private UsuarioModel cliente;
    // private String nomeCliente;
    private RestauranteApenasNomeModel restaurante;

}
