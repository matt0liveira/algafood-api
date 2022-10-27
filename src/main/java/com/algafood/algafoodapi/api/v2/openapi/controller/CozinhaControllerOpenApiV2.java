package com.algafood.algafoodapi.api.v2.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.v2.model.CozinhaModelV2;
import com.algafood.algafoodapi.api.v2.model.input.CozinhaInputModelV2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApiV2 {

    @ApiOperation("Lista todas as cozinhas")
    public PagedModel<CozinhaModelV2> listar(Pageable pageable);

    @ApiOperation("Busca uma cozinha pelo ID")
    public CozinhaModelV2 buscar(@ApiParam(value = "ID da cozinha") Long cozinhaId);

    @ApiOperation("Cadastra uma nova cozinha")
    public ResponseEntity<?> add(CozinhaInputModelV2 cozinhaInputDTO);

    @ApiOperation("Altera os dados de uma cozinha existente pelo ID")
    public ResponseEntity<CozinhaModelV2> alterar(@ApiParam(value = "ID da cozinha") Long cozinhaId,
            CozinhaInputModelV2 cozinhaInputDTO);

    @ApiOperation("Exclui uma cozinha pelo ID")
    public void remover(Long cozinhaId);

    @ApiOperation("Busca uma cozinha pelo NOME")
    public CollectionModel<CozinhaModelV2> buscarPorNome(String nome);
}
