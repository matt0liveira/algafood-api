package com.algafood.algafoodapi.api.v1.assembler.UsuarioAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.v1.model.input.UsuarioInputModel;
import com.algafood.algafoodapi.domain.models.Usuario;

@Component
public class UsuarioInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomainObject(UsuarioInputModel usuarioDTO) {
        return modelMapper.map(usuarioDTO, Usuario.class);
    }

    public void copyToDomainObject(UsuarioInputModel usuarioInputDTO, Usuario usuario) {
        modelMapper.map(usuarioInputDTO, usuario);
    }

}
