package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.v1.model.GrupoModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
	})
	@Operation(summary = "Lista todos os grupos associados a um usuário")
	public CollectionModel<GrupoModel> listar(@Parameter(description = "ID do usuário") Long usuarioId);

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Usuário e/ou grupo não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

			@ApiResponse(responseCode = "204", description = "Associação feita com sucesso")
	})
	@Operation(summary = "Associa um grupo a usuário")
	public ResponseEntity<Void> associar(@Parameter(description = "ID do usuário") Long usuarioId,
			@Parameter(description = "ID do grupo") Long grupoId);

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Usuário e/ou grupo não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

			@ApiResponse(responseCode = "204", description = "Desassociação feita com sucesso")
	})
	@Operation(summary = "Desassocia um grupo a usuário")
	public ResponseEntity<Void> desassociar(@Parameter(description = "ID do usuário") Long usuarioId,
			@Parameter(description = "ID do grupo") Long grupoId);
}
