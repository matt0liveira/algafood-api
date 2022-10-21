package com.algafood.algafoodapi.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.domain.filter.VendaDiariaFilter;
import com.algafood.algafoodapi.domain.models.dto.VendaDiaria;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Estatísticas")
public interface EstatisticasControllerOpenApi {

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restauranteId", value = "ID do restaurante", example = "1900-01-01T00:00:00"),
            @ApiImplicitParam(name = "dataCriacaoInicio", value = "Data inicial de criação", example = "1900-01-01T00:00:00"),
            @ApiImplicitParam(name = "dataCriacaoFim", value = "Data final de criação"),
            @ApiImplicitParam(name = "timeOffset", value = "Fuso horário", defaultValue = "+00:00")
    })
    @ApiOperation("Consulta de vendas diárias")
    public List<VendaDiaria> consultVendasDiarias(VendaDiariaFilter filter, String timeOffset);

    @ApiOperation("Consulta de vendas diárias")
    public ResponseEntity<byte[]> consultVendasDiariasPdf(VendaDiariaFilter filter, String timeOffset);
}
