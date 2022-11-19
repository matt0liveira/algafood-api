package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.v1.model.ProdutoModel;
import com.algafood.algafoodapi.api.v1.model.input.ProdutoInputModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
        })
        @Operation(summary = "Lista todos os produtos de um restaurante")
        public CollectionModel<ProdutoModel> listar(@Parameter(name = "ID do restaurante") Long restauranteId,
                        Boolean includeInativos);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante e/ou produto não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
        })
        @Operation(summary = "Busca um produto de um restaurante pelo ID")
        public ProdutoModel buscar(@Parameter(name = "ID do restaurante") Long restauranteId,
                        @Parameter(name = "ID do produto") Long produtoId);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso")
        })
        @Operation(summary = "Cadastra um produto")
        public ProdutoModel add(@Parameter(name = "ID do restaurante") Long restauranteId,
                        @Parameter(name = "Corpo", description = "Representação de um novo produto") ProdutoInputModel produtoInput);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante e/ou produto não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "200", description = "Produto alterado com sucesso")
        })
        @Operation(summary = "Altera os dados de um produto de um restaurante pelo ID")
        public ProdutoModel alterar(@Parameter(name = "ID do restaurante") Long restauranteId,
                        @Parameter(name = "ID do produto") Long produtoId,
                        @Parameter(name = "Corpo", description = "Representação de um produto com os dados atualizados") ProdutoInputModel produtoInput);
}
