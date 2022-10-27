package com.algafood.algafoodapi.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {

    private Long produtoId;
    private String produtoNome;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precototal;
    private String observacao;

}
