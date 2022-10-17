package com.algafood.algafoodapi.api.controllers.openapi;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.model.CidadeDTO;
import com.algafood.algafoodapi.api.model.input.CidadeInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista todas as cidades")
    public List<CidadeDTO> listar();

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Created")

    })
    public ResponseEntity<?> add(
            @ApiParam(name = "Corpo", value = "Representação de uma nova cidade") CidadeInputDTO cidadeInputDTO);

    @ApiOperation("Altera os dados do cadastro de uma cidade por id")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    public ResponseEntity<?> atualizar(@ApiParam(value = "ID de uma cidade", example = "1") Long cidadeId,
            @ApiParam(name = "Corpo", value = "Representação de uma cidade com os dados atualizados") CidadeInputDTO cidadeInputDTO);

    @ApiOperation("Remove uma cidade por id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cidade excluída"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    public void remover(@ApiParam("ID de uma cidade") Long cidadeId);
}
