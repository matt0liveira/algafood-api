package com.algafood.algafoodapi.api.v1.openapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.v1.model.FotoProdutoModel;
import com.algafood.algafoodapi.api.v1.model.input.FotoProdutoInputModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Produtos")
public interface RestauranteProdutoFotoControllerOpenApi {

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Restaurante e/ou produto não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
	})
	@Operation(summary = "Atualiza foto de um produto")
	public FotoProdutoModel atualizarFoto(@Parameter(name = "ID do restaurante") Long restauranteId,
			@Parameter(name = "ID do produto") Long produtoId,
			FotoProdutoInputModel fotoProdutoInput,
			@Parameter(name = "Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)", required = true) MultipartFile arquivo)
			throws IOException;

	@Operation(summary = "Busca a foto de um produto", responses = {
			@ApiResponse(responseCode = "404", description = "Restaurante e/ou produto não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),
			@ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class))),
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = FotoProdutoModel.class)),
					@Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary")),
					@Content(mediaType = "image/jpeg", schema = @Schema(type = "string", format = "binary"))
			})
	})
	public ResponseEntity<?> buscar(@Parameter(name = "ID do restaurante") Long restauranteId,
			@Parameter(name = "ID do produto") Long produtoId,
			@Parameter(hidden = true, required = false) String acceptHeader)
			throws HttpMediaTypeNotAcceptableException;

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Restaurante e/ou produto não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),
			@ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class))),
			@ApiResponse(responseCode = "204", description = "Foto excluída com sucesso")
	})
	@Operation(summary = "Exclui a foto do produto de um restaurante")
	public void excluir(@Parameter(name = "ID do restaurante") Long restauranteId,
			@Parameter(name = "ID do produto") Long produtoId);
}
