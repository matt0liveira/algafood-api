package com.algafood.algafoodapi.api.assembler.GrupoAssembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.model.GrupoDTO;
import com.algafood.algafoodapi.domain.models.Grupo;

@Component
public class GrupoModelAssembler {
    
    @Autowired
    private ModelMapper modelMapper;

    public GrupoDTO toDTO(Grupo grupo) {
        return modelMapper.map(grupo, GrupoDTO.class);
    }

    public List<GrupoDTO> toCollectionDTO(List<Grupo> grupos) {
        return grupos.stream()
            .map(grupo -> toDTO(grupo))
            .collect(Collectors.toList());
    }

}
