package com.algafood.algafoodapi.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.assembler.PermissaoAssembler.PermissaoModelAssembler;
import com.algafood.algafoodapi.api.model.PermissaoModel;
import com.algafood.algafoodapi.api.openapi.controller.PermissaoControllerOpenApi;
import com.algafood.algafoodapi.domain.models.Permissao;
import com.algafood.algafoodapi.domain.repository.PermissaoRepository;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController implements PermissaoControllerOpenApi {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private PermissaoModelAssembler permissaoModel;

    @GetMapping
    public CollectionModel<PermissaoModel> listar() {
        List<Permissao> permissoes = permissaoRepository.findAll();

        return permissaoModel.toCollectionModel(permissoes);
    }
}
