package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.v1.model.PermissaoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

        @ApiResponses({
                        @ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
        })
        @ApiOperation("Lista as permissões de um grupo")
        public CollectionModel<PermissaoModel> listar(@ApiParam(value = "ID do grupo") Long grupoId);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Grupo e/ou permissão não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Associação feita com sucesso")
        })
        @ApiOperation("Associa uma permissão a um grupo")
        public ResponseEntity<Void> associar(@ApiParam(value = "ID do grupo") Long grupoId,
                        @ApiParam(value = "ID da permissão") Long permissaoId);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Grupo e/ou permissão não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Desassociação feita com sucesso")
        })
        @ApiOperation("Desassocia uma permissão a um grupo")
        public ResponseEntity<Void> desassociar(@ApiParam(value = "ID do grupo") Long grupoId,
                        @ApiParam(value = "ID da permissão") Long permissaoId);
}
