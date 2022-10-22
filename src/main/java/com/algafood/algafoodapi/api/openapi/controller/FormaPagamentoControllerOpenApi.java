package com.algafood.algafoodapi.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.model.FormaPagamentoModel;
import com.algafood.algafoodapi.api.model.input.FormaPagamentoInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

        @ApiOperation("Lista todas as formas de pagamento")
        ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest req);

        @ApiResponses({
                        @ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
        })
        @ApiOperation("Busca uma forma de pagamento pelo ID")
        ResponseEntity<FormaPagamentoModel> buscar(
                        @ApiParam(value = "ID de uma forma de pagamento", example = "1") Long formaPagamentoId);

        @ApiResponses(@ApiResponse(responseCode = "201", description = "Forma de pagamento criada com sucesso"))
        @ApiOperation("Cadastra uma forma de pagamento")
        ResponseEntity<FormaPagamentoModel> add(
                        @ApiParam(value = "Corpo") FormaPagamentoInputDTO formaPagamentoInputDTO);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "200", description = "Forma de pagamento alterada com sucesso")
        })
        @ApiOperation("Altera os dados de uma forma de pagamento")
        ResponseEntity<FormaPagamentoModel> alterar(
                        @ApiParam(value = "ID de uma forma de pagamento", example = "1") Long formaPagamentoId,
                        @ApiParam(value = "Corpo") FormaPagamentoInputDTO formaPagamentoInputDTO);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Forma de pagamento excluída com sucesso")
        })
        @ApiOperation("Exclui uma forma de pagamento")
        void excluir(@ApiParam(value = "ID de uma forma de pagamento", example = "1") Long formaPagamentoId);
}
