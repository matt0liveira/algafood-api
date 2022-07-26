package com.algafood.algafoodapi.api.assembler.EstadoAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.model.input.EstadoInputDTO;
import com.algafood.algafoodapi.domain.models.Estado;

@Component
public class EstadoInputDisassembler {
    
    @Autowired
    private ModelMapper modelMapper;

    public Estado toDomainObject(EstadoInputDTO estadoInputDTO) {
        return modelMapper.map(estadoInputDTO, Estado.class);
    }

    public void copyToDomainOject(EstadoInputDTO estadoInputDTO, Estado estado) {
        modelMapper.map(estadoInputDTO, estado);
    }

}
