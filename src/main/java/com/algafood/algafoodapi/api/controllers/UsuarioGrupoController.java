package com.algafood.algafoodapi.api.controllers;

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

import com.algafood.algafoodapi.api.InstanceLink;
import com.algafood.algafoodapi.api.assembler.GrupoAssembler.GrupoModelAssembler;
import com.algafood.algafoodapi.api.model.GrupoModel;
import com.algafood.algafoodapi.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algafood.algafoodapi.domain.models.Usuario;
import com.algafood.algafoodapi.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(path = "/usuarios/{usuarioId}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @Autowired
    private InstanceLink instanceLink;

    @GetMapping
    public CollectionModel<GrupoModel> listar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuario.findOrFail(usuarioId);

        CollectionModel<GrupoModel> gruposModel = grupoModelAssembler.toCollectionModel(usuario.getGrupos())
                .add(instanceLink.linkToAssociacaoGrupoUsuario(usuario.getId(), "associar"));

        gruposModel.forEach(grupoModel -> {
            grupoModel.add(
                    instanceLink.linkToDesassociacaoGrupoUsuario(usuario.getId(), grupoModel.getId(), "desassociar"));
        });

        return gruposModel;
    }

    @PutMapping("/{grupoId}")
    public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuario.associarGrupo(usuarioId, grupoId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{grupoId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuario.desassociarGrupo(usuarioId, grupoId);

        return ResponseEntity.noContent().build();
    }
}
