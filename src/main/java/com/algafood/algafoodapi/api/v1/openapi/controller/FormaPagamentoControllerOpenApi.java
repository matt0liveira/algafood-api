package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.v1.model.FormaPagamentoModel;
import com.algafood.algafoodapi.api.v1.model.input.FormaPagamentoInputDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

        @Operation(summary = "Lista todas as formas de pagamento")
        ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest req);

        @Operation(summary = "Busca uma forma de pagamento por ID")
        @ApiResponses({
                        @ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
        })
        ResponseEntity<FormaPagamentoModel> buscar(
                        @Parameter(description = "ID da forma de pagamento") Long formaPagamentoId);

        @Operation(summary = "Cadastra uma forma de pagamento")
        @ApiResponses(@ApiResponse(responseCode = "201", description = "Forma de pagamento criada com sucesso"))
        ResponseEntity<FormaPagamentoModel> add(
                        @RequestBody(description = "Representação de uma nova forma de pagamento") FormaPagamentoInputDTO formaPagamentoInputDTO);

        @Operation(summary = "Altera os dados de uma forma de pagamento por ID")
        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "200", description = "Forma de pagamento alterada com sucesso")
        })
        ResponseEntity<FormaPagamentoModel> alterar(
                        @Parameter(description = "ID da forma de pagamento") Long formaPagamentoId,
                        @RequestBody(description = "Representação de uma forma de pagamento com dados atualizados") FormaPagamentoInputDTO formaPagamentoInputDTO);

        @Operation(summary = "Exclui uma forma de pagamento por ID")
        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Forma de pagamento excluída com sucesso")
        })
        void excluir(@Parameter(description = "ID da forma de pagamento") Long formaPagamentoId);
}
