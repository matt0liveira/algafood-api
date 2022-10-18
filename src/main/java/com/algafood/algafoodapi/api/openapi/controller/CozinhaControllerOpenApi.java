package com.algafood.algafoodapi.api.openapi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.model.CozinhaDTO;
import com.algafood.algafoodapi.api.model.input.CozinhaInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

	@ApiOperation("Lista todas as cozinhas")
	public Page<CozinhaDTO> listar(@PageableDefault(size = 10) Pageable pageable);

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

			@ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
	})
	@ApiOperation("Busca uma cozinha pelo ID")
	public CozinhaDTO buscar(@ApiParam(example = "1", value = "ID da cozinha") Long cozinhaId);

	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Cozinha cadastrada com sucesso")
	})
	@ApiOperation("Cadastra uma cozinha")
	public ResponseEntity<?> add(CozinhaInputDTO cozinhaInputDTO);

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
	})
	@ApiOperation("Altera os dados do cadastro de uma cozinha pelo ID")
	public ResponseEntity<CozinhaDTO> alterar(Long cozinhaId, CozinhaInputDTO cozinhaInputDTO);

	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

			@ApiResponse(responseCode = "204", description = "Cozinha excluída com sucesso")
	})
	@ApiOperation("Remove uma cozinha pelo ID")
	public void remover(Long cozinhaId);

	@ApiOperation("Busca uma cozinha pelo NOME")
	public List<CozinhaDTO> buscarPorNome(String nome);
}
