package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.v1.model.CozinhaModel;
import com.algafood.algafoodapi.api.v1.model.input.CozinhaInputModel;
import com.algafood.algafoodapi.core.springdoc.PageableParameter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cozinhas", description = "Gerencia as cozinhas")
public interface CozinhaControllerOpenApi {

	@PageableParameter
	@Operation(summary = "Lista todas as cozinhas")
	PagedModel<CozinhaModel> listar(@Parameter(hidden = true) @PageableDefault(size = 10) Pageable pageable);

	@Operation(summary = "Busca uma cozinha por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

			@ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
	})
	CozinhaModel buscar(@Parameter(description = "ID da cozinha", required = true) Long cozinhaId);

	@Operation(summary = "Cadastra uma cozinha")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Cozinha cadastrada com sucesso")
	})
	ResponseEntity<CozinhaModel> add(CozinhaInputModel cozinhaInputDTO);

	@Operation(summary = "Altera os dados de uma cozinha por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
	})
	ResponseEntity<CozinhaModel> alterar(@Parameter(description = "ID da cozinha", required = true) Long cozinhaId,
			@RequestBody(description = "Representação de uma nova cozinha") CozinhaInputModel cozinhaInputDTO);

	@Operation(summary = "Exclui uma cozinha por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

			@ApiResponse(responseCode = "204", description = "Cozinha excluída com sucesso")
	})
	void remover(@Parameter(description = "ID da cozinha", required = true) Long cozinhaId);

	@Operation(summary = "Busca uma cozinha por NOME")
	CollectionModel<CozinhaModel> buscarPorNome(
			@Parameter(description = "NOME da cozinha", required = true) String nome);
}
