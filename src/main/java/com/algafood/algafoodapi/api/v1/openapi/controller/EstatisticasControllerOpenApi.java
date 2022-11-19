package com.algafood.algafoodapi.api.v1.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.v1.controllers.EstatisticasController.EstatisticasModel;
import com.algafood.algafoodapi.domain.filter.VendaDiariaFilter;
import com.algafood.algafoodapi.domain.models.dto.VendaDiaria;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Estatísticas")
public interface EstatisticasControllerOpenApi {

	@Operation(summary = "Estatísticas")
	public EstatisticasModel root();

	@Parameters({
			@Parameter(name = "restauranteId", description = "ID do restaurante", example = "1900-01-01T00:00:00"),
			@Parameter(name = "dataCriacaoInicio", description = "Data inicial de criação", example = "1900-01-01T00:00:00"),
			@Parameter(name = "dataCriacaoFim", description = "Data final de criação"),
			@Parameter(name = "timeOffset", description = "Fuso horário")
	})
	@Operation(summary = "Consulta de vendas diárias")
	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
	})
	public List<VendaDiaria> consultVendasDiarias(VendaDiariaFilter filter, String timeOffset);

	@Operation(summary = "Consulta de vendas diárias")
	public ResponseEntity<byte[]> consultVendasDiariasPdf(VendaDiariaFilter filter, String timeOffset);
}
