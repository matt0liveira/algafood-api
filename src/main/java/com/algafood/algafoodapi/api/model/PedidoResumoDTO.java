package com.algafood.algafoodapi.api.model;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoResumoDTO {
    
    private Long id;
    private Long subtotal;
    private Long taxaFrete;
    private Long  valorTotal;
    private UsuarioDTO cliente;
    private RestauranteResumoDTO restaurante;
    private ItemPedidoDTO itemPedido;
    private String status;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;

}
