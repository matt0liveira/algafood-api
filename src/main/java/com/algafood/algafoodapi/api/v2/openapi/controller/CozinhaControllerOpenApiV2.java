package com.algafood.algafoodapi.api.v2.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.v2.model.CozinhaModelV2;
import com.algafood.algafoodapi.api.v2.model.input.CozinhaInputModelV2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cozinhas")
public interface CozinhaControllerOpenApiV2 {

    @Operation(summary = "Lista todas as cozinhas")
    public PagedModel<CozinhaModelV2> listar(Pageable pageable);

    @Operation(summary = "Busca uma cozinha pelo ID")
    public CozinhaModelV2 buscar(@Parameter(description = "ID da cozinha") Long cozinhaId);

    @Operation(summary = "Cadastra uma nova cozinha")
    public ResponseEntity<?> add(CozinhaInputModelV2 cozinhaInputDTO);

    @Operation(summary = "Altera os dados de uma cozinha existente pelo ID")
    public ResponseEntity<CozinhaModelV2> alterar(@Parameter(description = "ID da cozinha") Long cozinhaId,
            CozinhaInputModelV2 cozinhaInputDTO);

    @Operation(summary = "Exclui uma cozinha pelo ID")
    public void remover(Long cozinhaId);

    @Operation(summary = "Busca uma cozinha pelo NOME")
    public CollectionModel<CozinhaModelV2> buscarPorNome(String nome);
}
