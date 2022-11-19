package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.v1.model.CidadeModel;
import com.algafood.algafoodapi.api.v1.model.input.CidadeInputModel;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Tag(name = "Cidades")
public interface CidadeControllerOpenApi {

        @Operation(summary = "Lista todas as cidades")
        CollectionModel<CidadeModel> listar();

        @Operation(summary = "Busca uma cidade por ID")
        ResponseEntity<CidadeModel> buscar(@Parameter(description = "ID da cidade", required = true) Long cidadeId);

        @Operation(summary = "Cadastra uma cidade")
        @ApiResponses({ @ApiResponse(responseCode = "201", description = "Created")

        })
        ResponseEntity<?> add(
                        @RequestBody(description = "Representação de uma nova cidade") CidadeInputModel cidadeInputDTO);

        @Operation(summary = "Altera os dados de uma cidade por ID")
        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
        })
        ResponseEntity<?> atualizar(@Parameter(description = "ID da cidade", required = true) Long cidadeId,
                        @RequestBody(description = "Representação de uma cidade com dados atualizados") CidadeInputModel cidadeInputDTO);

        @Operation(summary = "Exclui uma cidade por ID")
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Cidade excluída"),
                        @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
        })
        void remover(@Parameter(description = "ID da cidade", required = true) Long cidadeId);
}
