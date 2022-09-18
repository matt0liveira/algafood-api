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
    public void confirmar(String codigo) {
        Pedido pedido = cadastroPedido.findOrFail(codigo);

        pedido.confirmar();
    }

    @Transactional
    public void entregar(String codigo) {
        Pedido pedido = cadastroPedido.findOrFail(codigo);

       pedido.entregar();
    }

    @Transactional
    public void cancelar(String codigo) {
        Pedido pedido = cadastroPedido.findOrFail(codigo);

       pedido.cancelar();
    }

}
