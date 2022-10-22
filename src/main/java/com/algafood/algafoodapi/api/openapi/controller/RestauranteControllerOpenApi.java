package com.algafood.algafoodapi.api.openapi.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.model.RestauranteModel;
import com.algafood.algafoodapi.api.model.input.RestauranteInputDTO;
import com.algafood.algafoodapi.domain.models.Restaurante;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

        @ApiOperation("Lista todos os restaurantes")
        MappingJacksonValue listar(String projecao);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
        })
        @ApiOperation("Busca um restaurante pelo ID")
        ResponseEntity<RestauranteModel> buscar(@ApiParam(value = "ID de um restaurante") Long restauranteId);

        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso")
        })
        @ApiOperation("Cadastra um restaurante")
        ResponseEntity<RestauranteModel> add(RestauranteInputDTO restauranteInputDTO);

        @ApiResponses(@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))))
        @ApiOperation("Altera os dados de um restaurante pelo ID")
        ResponseEntity<RestauranteModel> atualizar(@ApiParam(value = "ID de um restaurante") Long restauranteId,
                        RestauranteInputDTO restauranteInputDTO);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Restaurante excluído com sucesso")
        })
        @ApiOperation("Exclui um restaurante pelo ID")
        void excluir(@ApiParam(value = "ID de um restaurante") Long restauranteId);

        @ApiOperation("Busca restaurantes filtrando por nome, valor de frete inicial e valor de frete final")
        List<Restaurante> restaurantesPorNomeFrete(String nome, BigDecimal freteInicial, BigDecimal freteFinal);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso")
        })
        @ApiOperation("Ativa um restaurante")
        void ativar(@ApiParam(value = "ID de um restaurante") Long restauranteId);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso")
        })
        @ApiOperation("Inativa um restaurante")
        void inativar(@ApiParam(value = "ID de um restaurante") Long restauranteId);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso")
        })
        @ApiOperation("Abre um restaurante")
        void abrir(@ApiParam(value = "ID de um restaurante") Long restauranteId);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso")
        })
        @ApiOperation("Fecha um restaurante")
        void fechar(@ApiParam(value = "ID de um restaurante") Long restauranteId);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")
        })
        @ApiOperation("Ativa restaurantes em massa")
        void ativarEmMassa(@ApiParam(value = "IDs dos restaurantes") List<Long> restauranteIds);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Restaurantes inativados com sucesso")
        })
        @ApiOperation("Inativa restaurantes em massa")
        void inativarEmMassa(@ApiParam(value = "IDs dos restaurantes") List<Long> restauranteIds);
}