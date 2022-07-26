package com.algafood.algafoodapi.api.assembler.CidadeAssembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.model.CidadeDTO;
import com.algafood.algafoodapi.domain.models.Cidade;

@Component
public class CidadeModelAssembler {
    
    @Autowired
    private ModelMapper modelMapper;

    public CidadeDTO toDTO(Cidade cidade) {
        return modelMapper.map(cidade, CidadeDTO.class);
    }

    public List<CidadeDTO> toCollectionDTO(List<Cidade> cidades) {
        return cidades.stream()
            .map(cidade -> toDTO(cidade))
            .collect(Collectors.toList());       
    }

}
