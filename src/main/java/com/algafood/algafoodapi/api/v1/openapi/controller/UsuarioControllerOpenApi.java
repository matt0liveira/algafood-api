package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.v1.model.UsuarioModel;
import com.algafood.algafoodapi.api.v1.model.input.SenhaInput;
import com.algafood.algafoodapi.api.v1.model.input.UsuarioComSenhaInput;
import com.algafood.algafoodapi.api.v1.model.input.UsuarioInputModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuários", description = "Gerencia os usuários")
public interface UsuarioControllerOpenApi {

        @Operation(summary = "Lista todos os usuários")
        public CollectionModel<UsuarioModel> listar();

        @ApiResponses({
                        @ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
        })
        @Operation(summary = "Busca um usuário pelo ID")
        public UsuarioModel buscar(@Parameter(description = "ID do usuário") Long usuarioId);

        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
        })
        @Operation(summary = "Cadastra um novo usuário")
        public ResponseEntity<UsuarioModel> add(
                        @Parameter(name = "Corpo", description = "Representação de um novo usuário") UsuarioComSenhaInput usuarioInputDTO);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
        })
        @Operation(summary = "Altera os dados de um usuário pelo ID")
        public ResponseEntity<UsuarioModel> alterar(
                        @Parameter(name = "Corpo", description = "Representação de um usuário com os dados atualizados") UsuarioInputModel usuarioInputDTO,
                        @Parameter(description = "ID do usuário") Long usuarioId);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso")
        })
        @Operation(summary = "Altera a senha de um usuário")
        public void alterarSenha(@Parameter(description = "ID do usuário") Long usuarioId,
                        @Parameter(name = "Corpo", description = "Representação de modelo para alteração de senha") SenhaInput senha);
}
