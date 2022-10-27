package com.algafood.algafoodapi.api.v1.assembler.CozinhaAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.v1.model.input.CozinhaInputModel;
import com.algafood.algafoodapi.domain.models.Cozinha;

@Component
public class CozinhaInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cozinha toDomainObject(CozinhaInputModel cozinhaInputDTO) {
        return modelMapper.map(cozinhaInputDTO, Cozinha.class);
    }

    public void copyToDomainOject(CozinhaInputModel cozinhaInputDTO, Cozinha cozinha) {
        modelMapper.map(cozinhaInputDTO, cozinha);
    }

}
