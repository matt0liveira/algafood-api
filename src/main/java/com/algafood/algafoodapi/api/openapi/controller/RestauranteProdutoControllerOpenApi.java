package com.algafood.algafoodapi.api.openapi.controller;

import java.util.List;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.model.ProdutoDTO;
import com.algafood.algafoodapi.api.model.input.ProdutoInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

            @ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    @ApiOperation("Lista todos os produtos de um restaurante")
    public List<ProdutoDTO> listar(@ApiParam(value = "ID do restaurante") Long restauranteId, boolean includeInativos);

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Restaurante e/ou produto não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

            @ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    @ApiOperation("Busca um produto de um restaurante pelo ID")
    public ProdutoDTO buscar(@ApiParam(value = "ID do restaurante") Long restauranteId,
            @ApiParam(value = "ID do produto") Long produtoId);

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso")
    })
    @ApiOperation("Cadastra um produto")
    public ProdutoDTO add(@ApiParam(value = "ID do restaurante") Long restauranteId,
            @ApiParam(name = "Corpo", value = "Representação de um novo produto") ProdutoInputDTO produtoInput);

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Restaurante e/ou produto não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

            @ApiResponse(responseCode = "200", description = "Produto alterado com sucesso")
    })
    @ApiOperation("Altera os dados de um produto de um restaurante pelo ID")
    public ProdutoDTO alterar(@ApiParam(value = "ID do restaurante") Long restauranteId,
            @ApiParam(value = "ID do produto") Long produtoId,
            @ApiParam(name = "Corpo", value = "Representação de um produto com os dados atualizados") ProdutoInputDTO produtoInput);
}
