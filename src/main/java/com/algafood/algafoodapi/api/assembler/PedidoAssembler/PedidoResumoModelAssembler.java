package com.algafood.algafoodapi.api.assembler.PedidoAssembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.model.PedidoResumoDTO;
import com.algafood.algafoodapi.domain.models.Pedido;

@Component
public class PedidoResumoModelAssembler {
    
    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoDTO toDTO(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoDTO.class);
    }

    public List<PedidoResumoDTO> toCollectionDTO(List<Pedido> pedidos) {
        return pedidos.stream()
            .map(pedido -> toDTO(pedido))
            .collect(Collectors.toList());
    }
}
