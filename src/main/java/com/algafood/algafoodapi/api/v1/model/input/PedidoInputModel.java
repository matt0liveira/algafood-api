package com.algafood.algafoodapi.api.v1.model.input;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoInputModel {

    @Valid
    @NotNull
    private RestauranteRefInput restaurante;

    @Valid
    @NotNull
    private FormaPagamentoRefInput formaPagamento;

    @Valid
    @NotNull
    private EnderecoInputModel enderecoEntrega;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemPedidoInput> itens;

}
