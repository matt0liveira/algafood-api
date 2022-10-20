package com.algafood.algafoodapi.api.openapi.controller;

import java.util.List;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.model.GrupoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    @ApiOperation("Lista todos os grupos associados a um usuário")
    public List<GrupoDTO> listar(@ApiParam(value = "ID do usuário") Long usuarioId);

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Usuário e/ou grupo não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

            @ApiResponse(responseCode = "204", description = "Associação feita com sucesso")
    })
    @ApiOperation("Associa um grupo a usuário")
    public void associar(@ApiParam(value = "ID do usuário") Long usuarioId,
            @ApiParam(value = "ID do grupo") Long grupoId);

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Usuário e/ou grupo não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

            @ApiResponse(responseCode = "204", description = "Desassociação feita com sucesso")
    })
    @ApiOperation("Desassocia um grupo a usuário")
    public void desassociar(@ApiParam(value = "ID do usuário") Long usuarioId,
            @ApiParam(value = "ID do grupo") Long grupoId);
}
