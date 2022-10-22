package com.algafood.algafoodapi.api.assembler.GrupoAssembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.model.GrupoModel;
import com.algafood.algafoodapi.domain.models.Grupo;

@Component
public class GrupoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoModel toDTO(Grupo grupo) {
        return modelMapper.map(grupo, GrupoModel.class);
    }

    public List<GrupoModel> toCollectionDTO(Collection<Grupo> grupos) {
        return grupos.stream()
                .map(grupo -> toDTO(grupo))
                .collect(Collectors.toList());
    }

}
