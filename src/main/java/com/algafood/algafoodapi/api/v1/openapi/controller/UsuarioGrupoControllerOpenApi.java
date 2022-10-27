package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.v1.model.GrupoModel;

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
	public CollectionModel<GrupoModel> listar(@ApiParam(value = "ID do usuário") Long usuarioId);

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Usuário e/ou grupo não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

			@ApiResponse(responseCode = "204", description = "Associação feita com sucesso")
	})
	@ApiOperation("Associa um grupo a usuário")
	public ResponseEntity<Void> associar(@ApiParam(value = "ID do usuário") Long usuarioId,
			@ApiParam(value = "ID do grupo") Long grupoId);

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Usuário e/ou grupo não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

			@ApiResponse(responseCode = "204", description = "Desassociação feita com sucesso")
	})
	@ApiOperation("Desassocia um grupo a usuário")
	public ResponseEntity<Void> desassociar(@ApiParam(value = "ID do usuário") Long usuarioId,
			@ApiParam(value = "ID do grupo") Long grupoId);
}
