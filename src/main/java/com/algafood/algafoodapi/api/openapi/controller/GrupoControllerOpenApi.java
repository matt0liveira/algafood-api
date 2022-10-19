package com.algafood.algafoodapi.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.model.GrupoDTO;
import com.algafood.algafoodapi.api.model.input.GrupoInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

        @ApiOperation("Lista todos os grupos de permissões")
        List<GrupoDTO> listar();

        @ApiOperation("Busca um grupo pelo ID")
        GrupoDTO buscar(@ApiParam(value = "ID de um grupo", example = "1") Long grupoId);

        @ApiOperation("Cadastra um grupo de permissão")
        ResponseEntity<GrupoDTO> add(
                        @ApiParam(name = "Corpo", value = "Representação de um novo grupo") GrupoInputDTO grupoInput);

        @ApiOperation("Altera dados de cadastro do grupo de permissão pelo ID")
        ResponseEntity<GrupoDTO> alterar(@ApiParam(value = "ID de um grupo") Long grupoId,
                        @ApiParam(value = "Representação de um grupo atualizado") GrupoInputDTO grupoInput);

        @ApiOperation("Remove um grupo de permissão pelo ID")
        void remover(@ApiParam(value = "ID de um grupo", example = "1") Long grupoId);
}
