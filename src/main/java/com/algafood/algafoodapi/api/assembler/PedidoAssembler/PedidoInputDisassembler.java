package com.algafood.algafoodapi.api.assembler.PedidoAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.model.input.PedidoInputDTO;
import com.algafood.algafoodapi.domain.models.Pedido;

@Component
public class PedidoInputDisassembler {
    
    @Autowired
    private ModelMapper modelMapper;

    public Pedido toDomainObject(PedidoInputDTO pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }

    public void copyToDomainObject(PedidoInputDTO pedidoInput, Pedido pedido) {
        modelMapper.map(pedidoInput, pedido);
    }

}
