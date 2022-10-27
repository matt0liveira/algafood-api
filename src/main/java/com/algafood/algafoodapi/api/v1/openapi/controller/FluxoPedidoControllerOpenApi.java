package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Pedido confirmado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
	})
	@ApiOperation("Muda o status do pedido para CONFIRMADO pelo CODIGO")
	public ResponseEntity<Void> confirmar(@ApiParam(value = "CODIGO do pedido") String codigo);

	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Pedido entregue com sucesso"),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
	})
	@ApiOperation("Muda o status do pedido para ENTREGUE pelo CODIGO")
	public ResponseEntity<Void> entregar(@ApiParam(value = "CODIGO do pedido") String codigo);

	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Pedido cancelado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
	})
	@ApiOperation("Muda o status do pedido para CANCELADO pelo CODIGO")
	public ResponseEntity<Void> cancelar(@ApiParam(value = "CODIGO do pedido") String codigo);
}
