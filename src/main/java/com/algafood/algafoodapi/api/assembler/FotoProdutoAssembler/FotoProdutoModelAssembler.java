package com.algafood.algafoodapi.api.assembler.FotoProdutoAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.model.FotoProdutoDTO;
import com.algafood.algafoodapi.domain.models.FotoProduto;

@Component
public class FotoProdutoModelAssembler {
    
    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoDTO toDTO(FotoProduto foto) {
        return  modelMapper.map(foto, FotoProdutoDTO.class);
    }
}
