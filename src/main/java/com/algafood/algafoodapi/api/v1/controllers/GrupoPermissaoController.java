package com.algafood.algafoodapi.api.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.v1.InstanceLink;
import com.algafood.algafoodapi.api.v1.assembler.PermissaoAssembler.PermissaoModelAssembler;
import com.algafood.algafoodapi.api.v1.model.PermissaoModel;
import com.algafood.algafoodapi.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algafood.algafoodapi.domain.models.Grupo;
import com.algafood.algafoodapi.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(path = "v1/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

    @Autowired
    private InstanceLink instanceLink;

    @GetMapping
    public CollectionModel<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.findOrFail(grupoId);

        CollectionModel<PermissaoModel> permissoesModel = permissaoModelAssembler
                .toCollectionModel(grupo.getPermissoes())
                .add(instanceLink.linkToAssociacaoPermissoesGrupo(grupo.getId(), "associar"));

        permissoesModel.forEach(permissaoModel -> {
            permissaoModel.add(instanceLink.linkToDesassociacaoPermissoesGrupo(grupo.getId(), permissaoModel.getId(),
                    "desassociar"));
        });

        return permissoesModel;
    }

    @PutMapping("/{permissaoId}")
    public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.associarPermissao(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{permissaoId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.desassociarPermissao(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }
}