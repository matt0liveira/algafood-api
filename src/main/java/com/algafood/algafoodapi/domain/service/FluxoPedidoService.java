package com.algafood.algafoodapi.domain.service;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algafood.algafoodapi.domain.models.Pedido;
import com.algafood.algafoodapi.domain.repository.PedidoRepository;

@Service
public class FluxoPedidoService {
    
    @Autowired
    private CadastroPedidoService cadastroPedido;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public void confirmar(String codigo) {
        Pedido pedido = cadastroPedido.findOrFail(codigo);
        pedido.confirmar();

        pedidoRepository.save(pedido);
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

       pedidoRepository.save(pedido);
    }

}
