package com.algafood.algafoodapi.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.model.PedidoDTO;
import com.algafood.algafoodapi.api.model.PedidoResumoDTO;
import com.algafood.algafoodapi.api.model.input.PedidoInputDTO;
import com.algafood.algafoodapi.domain.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

    @ApiImplicitParams(@ApiImplicitParam(name = "fields", value = "Nome das propriedades para filtrar na resposta, separadas por vírgula", paramType = "query", type = "string"))
    @ApiOperation("Pesquisa de pedidos")
    public Page<PedidoResumoDTO> pesquisar(PedidoFilter filter, Pageable pageable);

    @ApiImplicitParams(@ApiImplicitParam(name = "fields", value = "Nome das propriedades para filtrar na resposta, separadas por vírgula", paramType = "query", type = "string"))
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    @ApiOperation("Busca de pedidos pelo CÓDIGO")
    public PedidoDTO buscar(@ApiParam(value = "Código do pedido") String codigo);

    @ApiOperation("Emissão de pedidos")
    public PedidoDTO emitir(PedidoInputDTO pedidoInput);
}
