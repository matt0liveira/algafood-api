package com.algafood.algafoodapi.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.model.UsuarioDTO;
import com.algafood.algafoodapi.api.model.input.SenhaInput;
import com.algafood.algafoodapi.api.model.input.UsuarioComSenhaInput;
import com.algafood.algafoodapi.api.model.input.UsuarioInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

    @ApiOperation("Lista todos os usuários")
    public List<UsuarioDTO> listar();

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    @ApiOperation("Busca um usuário pelo ID")
    public UsuarioDTO buscar(@ApiParam(value = "ID do usuário") Long usuarioId);

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
    })
    @ApiOperation("Cadastra um novo usuário")
    public UsuarioDTO add(
            @ApiParam(name = "Corpo", value = "Representação de um novo usuário") UsuarioComSenhaInput usuarioInputDTO);

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    @ApiOperation("Altera os dados de um usuário pelo ID")
    public ResponseEntity<UsuarioDTO> alterar(
            @ApiParam(name = "Corpo", value = "Representação de um usuário com os dados atualizados") UsuarioInputDTO usuarioInputDTO,
            @ApiParam(value = "ID do usuário") Long usuarioId);

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso")
    })
    @ApiOperation("Altera a senha de um usuário")
    public void alterarSenha(@ApiParam(value = "ID do usuário") Long usuarioId,
            @ApiParam(name = "Corpo", value = "Representação de modelo para alteração de senha") SenhaInput senha);
}
