package com.algafood.algafoodapi.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.assembler.PedidoAssembler.PedidoModelAssembler;
import com.algafood.algafoodapi.api.model.PedidoDTO;
import com.algafood.algafoodapi.domain.repository.PedidoRepository;
import com.algafood.algafoodapi.domain.service.CadastroPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoModelAssembler pedidoModel;

    @Autowired
    private CadastroPedidoService cadastroPedido;

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @GetMapping
    public List<PedidoDTO> listar() {
        return pedidoModel.toCollectionDTO(pedidoRepository.findAll());
    }

    @GetMapping("/{pedidoId}")
    public PedidoDTO buscar(@PathVariable Long pedidoId) {
        return pedidoModel.toDTO(cadastroPedido.findOrFail(pedidoId));
    }
}
