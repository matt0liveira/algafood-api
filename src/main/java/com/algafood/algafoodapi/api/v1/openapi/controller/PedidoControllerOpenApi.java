package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.v1.model.PedidoModel;
import com.algafood.algafoodapi.api.v1.model.PedidoResumoModel;
import com.algafood.algafoodapi.api.v1.model.input.PedidoInputModel;
import com.algafood.algafoodapi.core.springdoc.PageableParameter;
import com.algafood.algafoodapi.domain.filter.PedidoFilter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pedidos", description = "Gerencia os pedidos")
public interface PedidoControllerOpenApi {

	@PageableParameter
	@Parameter(in = ParameterIn.QUERY, name = "clienteId", description = "ID do cliente", schema = @Schema(type = "integer", example = "0"))
	@Parameter(in = ParameterIn.QUERY, name = "restauranteId", description = "ID do cliente", schema = @Schema(type = "integer", example = "0"))
	@Parameter(in = ParameterIn.QUERY, name = "dataCriacaoInicio", description = "Data inicial de criação", schema = @Schema(type = "datetime", example = "2022-11-22T00:00:00"))
	@Parameter(in = ParameterIn.QUERY, name = "dataCriacaoFim", description = "Data final de criação", schema = @Schema(type = "datetime", example = "2022-11-22T00:00:00"))
	@Operation(summary = "Pesquisa de pedidos")
	@Parameter(name = "fields", description = "Nome das propriedades para filtrar na resposta, separadas por vírgula")
	PagedModel<PedidoResumoModel> pesquisar(@Parameter(hidden = true) PedidoFilter filter,
			@Parameter(hidden = true) Pageable pageable);

	@Operation(summary = "Busca um pedido por CODIGO")
	@Parameters(@Parameter(name = "fields", description = "Nome das propriedades para filtrar na resposta, separadas por vírgula"))
	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),
			@ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso", content = @Content(schema = @Schema(implementation = PedidoModel.class)))
	})
	PedidoModel buscar(@Parameter(description = "Código do pedido") String codigo);

	@Operation(summary = "Emite um novo pedido")
	PedidoModel emitir(@RequestBody(description = "Representação de um novo pedido") PedidoInputModel pedidoInput);
}
