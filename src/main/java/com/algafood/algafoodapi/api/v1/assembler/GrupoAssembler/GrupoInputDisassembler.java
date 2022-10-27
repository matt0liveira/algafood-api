package com.algafood.algafoodapi.api.v1.assembler.GrupoAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.v1.model.input.GrupoInputModel;
import com.algafood.algafoodapi.domain.models.Grupo;

@Component
public class GrupoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Grupo toDomainObject(GrupoInputModel grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }

    public void copyToDomainObject(GrupoInputModel grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    }
}
