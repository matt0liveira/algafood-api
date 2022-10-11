package com.algafood.algafoodapi.domain.event;

import com.algafood.algafoodapi.domain.models.Pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoCanceladoEvent {
    private Pedido pedido;
}
