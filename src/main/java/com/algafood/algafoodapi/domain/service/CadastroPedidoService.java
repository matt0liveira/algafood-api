package com.algafood.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algafood.algafoodapi.domain.exceptions.PedidoNotfoundException;
import com.algafood.algafoodapi.domain.models.Pedido;
import com.algafood.algafoodapi.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido findOrFail(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new PedidoNotfoundException(pedidoId));
    }
}
