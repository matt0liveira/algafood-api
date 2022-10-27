package com.algafood.algafoodapi.api.v2.assembler.CozinhaAssemblerV2;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.v2.model.input.CozinhaInputModelV2;
import com.algafood.algafoodapi.domain.models.Cozinha;

@Component
public class CozinhaInputDisassemblerV2 {

    @Autowired
    private ModelMapper modelMapper;

    public Cozinha toDomainObject(CozinhaInputModelV2 cozinhaInputDTO) {
        return modelMapper.map(cozinhaInputDTO, Cozinha.class);
    }

    public void copyToDomainOject(CozinhaInputModelV2 cozinhaInputDTO, Cozinha cozinha) {
        modelMapper.map(cozinhaInputDTO, cozinha);
    }

}
