package com.algafood.algafoodapi.domain.models.enums;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {

    CRIADO("Criado"),
    CONFIRMADO("Confirmado", CRIADO),
    ENTREGUE("Entregue", CONFIRMADO),
    CANCELADO("Cancelado", CRIADO);

    private String descricao;
    private List<StatusPedido> statusCompativeis;

    StatusPedido(String descricao, StatusPedido... statusCompativeis) {
        this.descricao = descricao;
        this.statusCompativeis = Arrays.asList(statusCompativeis);
    }

    public String getDescricao() {
        return this.descricao;
    }

    public boolean cantUpdateTo(StatusPedido newStatus) {
        return !newStatus.statusCompativeis.contains(this);
    }

    public boolean canUpdateTo(StatusPedido newStatus) {
        return !cantUpdateTo(newStatus);
    }

}
