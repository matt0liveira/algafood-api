package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.v1.model.FormaPagamentoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
        })
        @ApiOperation("Lista as formas de pagamento associadas a um restaurante")
        public CollectionModel<FormaPagamentoModel> listar(@ApiParam(value = "ID do restaurante") Long restauranteId);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante e/ou forma de pagamento não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Forma de pagamento desassociada com sucesso")
        })
        @ApiOperation("Desassocia uma forma de pagamento de um restaurante")
        public ResponseEntity<Void> desassociar(@ApiParam(value = "ID do restaurante") Long restauranteId,
                        @ApiParam(value = "ID da forma de pagamento") Long formaPagamentoId);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante e/ou forma de pagamento não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Forma de pagamento associada com sucesso")
        })
        @ApiOperation("Associa uma forma de pagamento a restaurante")
        public ResponseEntity<Void> associar(@ApiParam(value = "ID do restaurante") Long restauranteId,
                        @ApiParam(value = "ID da forma de pagamento") Long formaPagamentoId);
}