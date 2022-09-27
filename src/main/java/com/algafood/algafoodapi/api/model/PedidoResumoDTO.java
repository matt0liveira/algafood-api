package com.algafood.algafoodapi.api.model;

import java.time.OffsetDateTime;

// import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Getter;
import lombok.Setter;

// @JsonFilter("pedidoFilter")
@Setter
@Getter
public class PedidoResumoDTO {
    
    private String codigo;
    private Long subtotal;
    private Long taxaFrete;
    private Long  valorTotal;
    private ItemPedidoDTO itemPedido;
    private String status;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;
    private UsuarioDTO cliente;
    private RestauranteResumoDTO restaurante;

}
