package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.v1.model.EstadoModel;
import com.algafood.algafoodapi.api.v1.model.input.EstadoInputModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Estados")
public interface EstadoControllerOpenApi {

        @Operation(summary = "Lista todos os estados")
        CollectionModel<EstadoModel> listar();

        @Operation(summary = "Busca um estado por ID")
        ResponseEntity<EstadoModel> buscar(@Parameter(description = "ID do estado", required = true) Long estadoId);

        @Operation(summary = "Cadastra um estado")
        ResponseEntity<EstadoModel> add(
                        @RequestBody(description = "Representação de um novo estado") EstadoInputModel estadoInputDTO);

        @Operation(summary = "Altera os dados de um estado por ID")
        @ApiResponses(@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))))
        ResponseEntity<EstadoModel> atualizar(@Parameter(description = "ID do estado", required = true) Long estadoId,
                        @RequestBody(description = "Representação de um estado com dados atualizados") EstadoInputModel estadoInputDTO);

        @Operation(summary = "Exclui um estado por ID")
        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Estado excluído com sucesso")

        })
        void remover(@Parameter(description = "ID do estado", required = true) Long estadoId);
}
