package com.algafood.algafoodapi.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.model.EstadoDTO;
import com.algafood.algafoodapi.api.model.input.EstadoInputDTO;

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
    List<EstadoDTO> listar();

    @ApiOperation("Cadastra um novo estado")
    ResponseEntity<EstadoDTO> add(@ApiParam(value = "corpo") EstadoInputDTO estadoInputDTO);

    @ApiResponses(@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))))
    @ApiOperation("Altera os dados de um estado pelo ID")
    ResponseEntity<EstadoDTO> atualizar(@ApiParam(value = "ID do estado") Long estadoId,
            @ApiParam(value = "corpo") EstadoInputDTO estadoInputDTO);

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

            @ApiResponse(responseCode = "204", description = "Estado excluído com sucesso")

    })
    @ApiOperation("Exclui um estado pelo ID")
    void remover(@ApiParam(value = "ID do estado") Long estadoId);
}
