package com.algafood.algafoodapi.api.assembler.EstadoAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.model.input.EstadoInputModel;
import com.algafood.algafoodapi.domain.models.Estado;

@Component
public class EstadoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Estado toDomainObject(EstadoInputModel estadoInputDTO) {
        return modelMapper.map(estadoInputDTO, Estado.class);
    }

    public void copyToDomainOject(EstadoInputModel estadoInputDTO, Estado estado) {
        modelMapper.map(estadoInputDTO, estado);
    }

}
