package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

	@Operation(summary = "Muda o status do pedido para CONFIRMADO")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Pedido confirmado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
	})
	public ResponseEntity<Void> confirmar(@Parameter(description = "CODIGO do pedido", required = true) String codigo);

	@Operation(summary = "Muda o status do pedido para ENTREGUE")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Pedido entregue com sucesso"),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
	})
	public ResponseEntity<Void> entregar(@Parameter(description = "CODIGO do pedido", required = true) String codigo);

	@Operation(summary = "Muda o status do pedido para CANCELADO")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Pedido cancelado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
	})
	public ResponseEntity<Void> cancelar(@Parameter(description = "CODIGO do pedido", required = true) String codigo);
}
