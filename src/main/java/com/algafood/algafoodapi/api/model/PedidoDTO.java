package com.algafood.algafoodapi.api.model;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoDTO {
    
    private String codigo;
    private Long subtotal;
    private Long taxaFrete;
    private Long  valorTotal;
    private UsuarioDTO cliente;
    private RestauranteResumoDTO restaurante;
    private FormaPagamentoDTO formaPagamento;
    private ItemPedidoDTO itemPedido;
    private EnderecoDTO endereco;
    private String status;
    private List<ItemPedidoDTO> itens;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;

}
