package com.algafood.algafoodapi.api.v2.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.v2.model.CidadeModelV2;
import com.algafood.algafoodapi.api.v2.model.input.CidadeInputModelV2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApiV2 {

    @ApiOperation("Lista todas as cidades")
    public CollectionModel<CidadeModelV2> listar();

    @ApiOperation("Busca uma cidade pelo ID")
    public ResponseEntity<CidadeModelV2> buscar(@ApiParam(value = "ID da cidade") Long cidadeId);

    @ApiOperation("Cadastra uma nova cidade")
    public ResponseEntity<?> add(CidadeInputModelV2 cidadeInputDTO);

    @ApiOperation("Altera os dados de uma cidade existente")
    public ResponseEntity<?> atualizar(@ApiParam(value = "ID da cidade") Long cidadeId,
            CidadeInputModelV2 cidadeInputDTO);
}
