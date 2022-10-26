package com.algafood.algafoodapi.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.model.GrupoModel;
import com.algafood.algafoodapi.api.model.input.GrupoInputModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

        @ApiOperation("Lista todos os grupos de permissões")
        CollectionModel<GrupoModel> listar();

        @ApiOperation("Busca um grupo pelo ID")
        GrupoModel buscar(@ApiParam(value = "ID de um grupo", example = "1") Long grupoId);

        @ApiOperation("Cadastra um grupo de permissão")
        ResponseEntity<GrupoModel> add(
                        @ApiParam(name = "Corpo", value = "Representação de um novo grupo") GrupoInputModel grupoInput);

        @ApiOperation("Altera dados de cadastro do grupo de permissão pelo ID")
        ResponseEntity<GrupoModel> alterar(@ApiParam(value = "ID de um grupo") Long grupoId,
                        @ApiParam(value = "Representação de um grupo atualizado") GrupoInputModel grupoInput);

        @ApiOperation("Remove um grupo de permissão pelo ID")
        void remover(@ApiParam(value = "ID de um grupo", example = "1") Long grupoId);
}
