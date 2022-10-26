package com.algafood.algafoodapi.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.model.UsuarioModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioControllerOpenApi {

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class)))
        })
        @ApiOperation("Lista todos responsáveis por um restaurante")
        public CollectionModel<UsuarioModel> listar(@ApiParam(value = "ID do restaurante") Long restauranteId);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante e/ou usuário não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Associação feita com sucesso")
        })
        @ApiOperation("Associa um responsável a um restaurante")
        public ResponseEntity<Void> associar(@ApiParam(value = "ID do restaurante") Long restauranteId,
                        @ApiParam(value = "ID do responsável") Long responsavelId);

        @ApiResponses({
                        @ApiResponse(responseCode = "404", description = "Restaurante e/ou usuário não encontrado", content = @Content(schema = @Schema(implementation = ErrorApi.class))),

                        @ApiResponse(responseCode = "204", description = "Desassociação feita com sucesso")
        })
        @ApiOperation("Desassocia um responsável de um restaurante")
        public ResponseEntity<Void> desassociar(@ApiParam(value = "ID do restaurante") Long restauranteId,
                        @ApiParam(value = "ID do responsável") Long responsavelId);
}
