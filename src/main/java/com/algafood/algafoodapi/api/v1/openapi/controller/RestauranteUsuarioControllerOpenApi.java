package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.v1.model.UsuarioModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Restaurantes")
public interface RestauranteUsuarioControllerOpenApi {

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
        })
        @Operation(summary = "Lista todos responsáveis por um restaurante")
        public CollectionModel<UsuarioModel> listar(@Parameter(name = "ID do restaurante") Long restauranteId);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante e/ou usuário não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Associação feita com sucesso")
        })
        @Operation(summary = "Associa um responsável a um restaurante")
        public ResponseEntity<Void> associar(@Parameter(name = "ID do restaurante") Long restauranteId,
                        @Parameter(name = "ID do responsável") Long responsavelId);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante e/ou usuário não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Desassociação feita com sucesso")
        })
        @Operation(summary = "Desassocia um responsável de um restaurante")
        public ResponseEntity<Void> desassociar(@Parameter(name = "ID do restaurante") Long restauranteId,
                        @Parameter(name = "ID do responsável") Long responsavelId);
}
