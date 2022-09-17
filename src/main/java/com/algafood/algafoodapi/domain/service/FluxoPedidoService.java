package com.algafood.algafoodapi.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algafood.algafoodapi.domain.models.Pedido;

@Service
public class FluxoPedidoService {
    
    @Autowired
    private CadastroPedidoService cadastroPedido;

    @Transactional
    public void confirmar(Long pedidoId) {
        Pedido pedido = cadastroPedido.findOrFail(pedidoId);

        pedido.confirmar();
    }

    @Transactional
    public void entregar(Long pedidoId) {
        Pedido pedido = cadastroPedido.findOrFail(pedidoId);

       pedido.entregar();
    }

    @Transactional
    public void cancelar(Long pedidoId) {
        Pedido pedido = cadastroPedido.findOrFail(pedidoId);

       pedido.cancelar();
    }

}
