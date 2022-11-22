package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algafood.algafoodapi.api.v1.model.PermissaoModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Permissões", description = "Gerencia as permissões")
public interface PermissaoControllerOpenApi {

    @Operation(summary = "Lista todas as permissões")
    public CollectionModel<PermissaoModel> listar();
}
