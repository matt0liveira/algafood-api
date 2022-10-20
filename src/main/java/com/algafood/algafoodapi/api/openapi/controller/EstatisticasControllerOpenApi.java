package com.algafood.algafoodapi.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.domain.filter.VendaDiariaFilter;
import com.algafood.algafoodapi.domain.models.dto.VendaDiaria;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Estatísticas")
public interface EstatisticasControllerOpenApi {

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
