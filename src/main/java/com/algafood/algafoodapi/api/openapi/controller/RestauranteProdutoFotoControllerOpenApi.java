package com.algafood.algafoodapi.api.openapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.model.FotoProdutoDTO;
import com.algafood.algafoodapi.api.model.input.FotoProdutoInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteProdutoFotoControllerOpenApi {

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Restaurante e/ou produto não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    @ApiOperation("Insere uma nova foto no produto")
    public FotoProdutoDTO atualizarFoto(@ApiParam(value = "ID do restaurante") Long restauranteId,
            @ApiParam(value = "ID do produto") Long produtoId,
            FotoProdutoInputDTO fotoProdutoInput) throws IOException;

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Restaurante e/ou produto não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class))),
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = FotoProdutoDTO.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "image/png")),
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "image/jpeg"))
    })
    @ApiOperation(value = "Busca a foto do produto de um restaurante", produces = "image/jpeg, image/png, application/json")
    public ResponseEntity<?> buscar(@ApiParam(value = "ID do restaurante") Long restauranteId,
            @ApiParam(value = "ID do produto") Long produtoId,
            @ApiParam(hidden = true, required = false) String acceptHeader) throws HttpMediaTypeNotAcceptableException;

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Restaurante e/ou produto não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class))),
            @ApiResponse(responseCode = "204", description = "Foto excluída com sucesso")
    })
    @ApiOperation("Exclui a foto do produto de um restaurante")
    public void excluir(@ApiParam(value = "ID do restaurante") Long restauranteId,
            @ApiParam(value = "ID do produto") Long produtoId);
}
