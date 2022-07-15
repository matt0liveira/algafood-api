package com.algafood.algafoodapi.api.assembler.CozinhaAssembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.model.CozinhaDTO;
import com.algafood.algafoodapi.domain.models.Cozinha;

@Component
public class CozinhaModelAssembler {
    
    @Autowired
    private ModelMapper modelMapper;

    public CozinhaDTO toDTO(Cozinha cozinha) {
        return  modelMapper.map(Cozinha.class, CozinhaDTO.class);
    }

    public List<CozinhaDTO> toCollectionDTO(List<Cozinha> cozinhas) {
        return cozinhas.stream()
            .map(cozinha -> toDTO(cozinha))
            .collect(Collectors.toList());
    }

}
