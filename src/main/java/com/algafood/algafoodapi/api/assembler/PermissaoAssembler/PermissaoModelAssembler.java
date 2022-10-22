package com.algafood.algafoodapi.api.assembler.PermissaoAssembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.model.PermissaoModel;
import com.algafood.algafoodapi.domain.models.Permissao;

@Component
public class PermissaoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoModel toDTO(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoModel.class);
    }

    public List<PermissaoModel> toCollectionDTO(List<Permissao> permissoes) {
        return permissoes.stream()
                .map(permissao -> toDTO(permissao))
                .collect(Collectors.toList());
    }
}
