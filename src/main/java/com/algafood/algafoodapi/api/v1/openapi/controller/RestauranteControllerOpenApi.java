package com.algafood.algafoodapi.api.v1.openapi.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.v1.model.RestauranteApenasNomeModel;
import com.algafood.algafoodapi.api.v1.model.RestauranteModel;
import com.algafood.algafoodapi.api.v1.model.RestauranteResumoModel;
import com.algafood.algafoodapi.api.v1.model.input.RestauranteInputDTO;
import com.algafood.algafoodapi.domain.models.Restaurante;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Restaurantes")
public interface RestauranteControllerOpenApi {

	@Operation(summary = "Lista todos os restaurantes", hidden = true)
	CollectionModel<RestauranteApenasNomeModel> listar(String projecao);

	@Operation(summary = "Lista todos os restaurantes")
	CollectionModel<RestauranteResumoModel> listar();

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

			@ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
	})
	@Operation(summary = "Busca um restaurante pelo ID")
	ResponseEntity<RestauranteModel> buscar(@Parameter(description = "ID de um restaurante") Long restauranteId);

	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso")
	})
	@Operation(summary = "Cadastra um restaurante")
	ResponseEntity<RestauranteModel> add(
			@RequestBody(description = "Representação de um novo restaurante") RestauranteInputDTO restauranteInputDTO);

	@ApiResponses(@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))))
	@Operation(summary = "Altera os dados de um restaurante pelo ID")
	ResponseEntity<RestauranteModel> atualizar(@Parameter(description = "ID de um restaurante") Long restauranteId,
			@RequestBody(description = "Representação de um restaurante com dados atualizados") RestauranteInputDTO restauranteInputDTO);

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

			@ApiResponse(responseCode = "204", description = "Restaurante excluído com sucesso")
	})
	@Operation(summary = "Exclui um restaurante pelo ID")
	void excluir(@Parameter(description = "ID de um restaurante") Long restauranteId);

	@Operation(summary = "Busca restaurantes filtrando por nome, valor de frete inicial e valor de frete final")
	List<Restaurante> restaurantesPorNomeFrete(String nome, BigDecimal freteInicial, BigDecimal freteFinal);

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

			@ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso")
	})
	@Operation(summary = "Ativa um restaurante")
	ResponseEntity<Void> ativar(@Parameter(description = "ID de um restaurante") Long restauranteId);

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

			@ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso")
	})
	@Operation(summary = "Inativa um restaurante")
	ResponseEntity<Void> inativar(@Parameter(description = "ID de um restaurante") Long restauranteId);

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

			@ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso")
	})
	@Operation(summary = "Abre um restaurante")
	ResponseEntity<Void> abrir(@Parameter(description = "ID de um restaurante") Long restauranteId);

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

			@ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso")
	})
	@Operation(summary = "Fecha um restaurante")
	ResponseEntity<Void> fechar(@Parameter(description = "ID de um restaurante") Long restauranteId);

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

			@ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")
	})
	@Operation(summary = "Ativa restaurantes em massa")
	ResponseEntity<Void> ativarEmMassa(@Parameter(description = "IDs dos restaurantes") List<Long> restauranteIds);

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

			@ApiResponse(responseCode = "204", description = "Restaurantes inativados com sucesso")
	})
	@Operation(summary = "Inativa restaurantes em massa")
	ResponseEntity<Void> inativarEmMassa(@Parameter(description = "IDs dos restaurantes") List<Long> restauranteIds);
}