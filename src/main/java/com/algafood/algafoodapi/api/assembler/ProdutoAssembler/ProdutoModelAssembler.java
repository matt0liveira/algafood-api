package com.algafood.algafoodapi.api.assembler.ProdutoAssembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.model.ProdutoDTO;
import com.algafood.algafoodapi.domain.models.Produto;

@Component
public class ProdutoModelAssembler {
    
    @Autowired
    private ModelMapper modelMapper;

    public ProdutoDTO toDTO(Produto produto) {
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> toCollectionDTO(List<Produto> produtos) {
        return produtos.stream()
            .map(produto -> toDTO(produto))
            .collect(Collectors.toList());
    }
}