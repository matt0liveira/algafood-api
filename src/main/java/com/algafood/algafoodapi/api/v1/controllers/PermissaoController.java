package com.algafood.algafoodapi.api.v1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.v1.assembler.PermissaoAssembler.PermissaoModelAssembler;
import com.algafood.algafoodapi.api.v1.model.PermissaoModel;
import com.algafood.algafoodapi.api.v1.openapi.controller.PermissaoControllerOpenApi;
import com.algafood.algafoodapi.core.security.CheckSecurity;
import com.algafood.algafoodapi.domain.models.Permissao;
import com.algafood.algafoodapi.domain.repository.PermissaoRepository;

@RestController
@RequestMapping("v1/permissoes")
public class PermissaoController implements PermissaoControllerOpenApi {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private PermissaoModelAssembler permissaoModel;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsutar
    @Override
    @GetMapping
    public CollectionModel<PermissaoModel> listar() {
        List<Permissao> permissoes = permissaoRepository.findAll();

        return permissaoModel.toCollectionModel(permissoes);
    }
}
