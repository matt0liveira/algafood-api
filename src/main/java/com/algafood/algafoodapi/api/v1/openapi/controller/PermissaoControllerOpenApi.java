package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algafood.algafoodapi.api.v1.model.PermissaoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {

    @ApiOperation("Lista todas as permissões")
    public CollectionModel<PermissaoModel> listar();
}