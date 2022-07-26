package com.algafood.algafoodapi.api.assembler.CidadeAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.model.input.CidadeInputDTO;
import com.algafood.algafoodapi.domain.models.Cidade;

@Component
public class CidadeInputDisassembler {
    
    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeInputDTO cidadeInputDTO) {
        return modelMapper.map(cidadeInputDTO, Cidade.class);
    }

    public void copyToDomainOject(CidadeInputDTO cidadeInputDTO, Cidade cidade) {
        modelMapper.map(cidadeInputDTO, cidade);
    }

}
