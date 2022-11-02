package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.v1.model.EstadoModel;
import com.algafood.algafoodapi.api.v1.model.input.EstadoInputModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

        @ApiOperation("Lista todos os estados")
        CollectionModel<EstadoModel> listar();

        @ApiOperation("Busca um estado pelo ID")
        ResponseEntity<EstadoModel> buscar(@ApiParam(value = "ID do estado") Long estadoId);

        @ApiOperation("Cadastra um novo estado")
        ResponseEntity<EstadoModel> add(@ApiParam(value = "corpo") EstadoInputModel estadoInputDTO);

        @ApiResponses(@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))))
        @ApiOperation("Altera os dados de um estado pelo ID")
        ResponseEntity<EstadoModel> atualizar(@ApiParam(value = "ID do estado") Long estadoId,
                        @ApiParam(value = "corpo") EstadoInputModel estadoInputDTO);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Estado excluído com sucesso")

        })
        @ApiOperation("Exclui um estado pelo ID")
        void remover(@ApiParam(value = "ID do estado") Long estadoId);
}
