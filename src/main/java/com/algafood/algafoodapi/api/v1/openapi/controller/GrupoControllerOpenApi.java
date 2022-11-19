package com.algafood.algafoodapi.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algafood.algafoodapi.api.v1.model.GrupoModel;
import com.algafood.algafoodapi.api.v1.model.input.GrupoInputModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Grupos")
public interface GrupoControllerOpenApi {

        @Operation(summary = "Lista todos os grupos de permissões")
        CollectionModel<GrupoModel> listar();

        @Operation(summary = "Busca um grupo de permissão por ID")
        GrupoModel buscar(@Parameter(description = "ID do grupo de permissão") Long grupoId);

        @Operation(summary = "Cadastra um grupo de permissão")
        ResponseEntity<GrupoModel> add(
                        @RequestBody(description = "Representação de um novo grupo de permissão") GrupoInputModel grupoInput);

        @Operation(summary = "Altera os dados de um grupo de permissão por ID")
        ResponseEntity<GrupoModel> alterar(@Parameter(description = "ID do grupo de permissão") Long grupoId,
                        @RequestBody(description = "Representação de um grupo de permissão com dados atualizados") GrupoInputModel grupoInput);

        @Operation(summary = "Exclui um grupo de permissão por ID")
        void remover(@Parameter(description = "ID do grupo de permissão") Long grupoId);
}
