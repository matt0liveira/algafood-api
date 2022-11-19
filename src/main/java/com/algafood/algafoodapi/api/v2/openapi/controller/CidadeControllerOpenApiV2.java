package com.algafood.algafoodapi.api.v2.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.v2.model.CidadeModelV2;
import com.algafood.algafoodapi.api.v2.model.input.CidadeInputModelV2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cidades")
public interface CidadeControllerOpenApiV2 {

    @Operation(summary = "Lista todas as cidades")
    public CollectionModel<CidadeModelV2> listar();

    @Operation(summary = "Busca uma cidade pelo ID")
    public ResponseEntity<CidadeModelV2> buscar(@Parameter(description = "ID da cidade") Long cidadeId);

    @Operation(summary = "Cadastra uma nova cidade")
    public ResponseEntity<?> add(CidadeInputModelV2 cidadeInputDTO);

    @Operation(summary = "Altera os dados de uma cidade existente")
    public ResponseEntity<?> atualizar(@Parameter(description = "ID da cidade") Long cidadeId,
            CidadeInputModelV2 cidadeInputDTO);
}
