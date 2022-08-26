package com.algafood.algafoodapi.api.assembler.UsuarioAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.model.input.UsuarioInputDTO;
import com.algafood.algafoodapi.domain.models.Usuario;

@Component
public class UsuarioInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomainObject(UsuarioInputDTO usuarioDTO) {
        return modelMapper.map(usuarioDTO, Usuario.class);
    }

    public void copyToDomainObject(UsuarioInputDTO usuarioInputDTO, Usuario usuario) {
        modelMapper.map(usuarioInputDTO, usuario);
    }
    
}
