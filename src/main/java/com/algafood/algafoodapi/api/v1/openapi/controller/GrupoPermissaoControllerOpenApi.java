package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.v1.model.PermissaoModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

        @Operation(summary = "Lista todas as permissões de um grupo por ID")
        @ApiResponses({
                        @ApiResponse(responseCode = "400", description = "Requisição inválida (erro do cliente)", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
        })
        public CollectionModel<PermissaoModel> listar(@Parameter(description = "ID do grupo") Long grupoId);

        @Operation(summary = "Associa uma permissão a um grupo por ID")
        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Grupo e/ou permissão não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Associação feita com sucesso")
        })
        public ResponseEntity<Void> associar(@Parameter(description = "ID do grupo") Long grupoId,
                        @Parameter(description = "ID da permissão") Long permissaoId);

        @Operation(summary = "Desassocia uma permissão a um grupo por ID")
        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Grupo e/ou permissão não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Desassociação feita com sucesso")
        })
        public ResponseEntity<Void> desassociar(@Parameter(description = "ID do grupo") Long grupoId,
                        @Parameter(description = "ID da permissão") Long permissaoId);
}
