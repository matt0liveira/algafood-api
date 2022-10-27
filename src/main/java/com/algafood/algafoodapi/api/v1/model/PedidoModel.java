package com.algafood.algafoodapi.api.v1.model;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoModel extends RepresentationModel<PedidoModel> {

    private String codigo;
    private Long subtotal;
    private Long taxaFrete;
    private Long valorTotal;
    private UsuarioModel cliente;
    private RestauranteApenasNomeModel restaurante;
    private FormaPagamentoModel formaPagamento;
    private ItemPedidoModel itemPedido;
    private EnderecoModel endereco;
    private String status;
    private List<ItemPedidoModel> itens;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;

}
