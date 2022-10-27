package com.algafood.algafoodapi.api.v1.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.v1.assembler.GrupoAssembler.GrupoInputDisassembler;
import com.algafood.algafoodapi.api.v1.assembler.GrupoAssembler.GrupoModelAssembler;
import com.algafood.algafoodapi.api.v1.model.GrupoModel;
import com.algafood.algafoodapi.api.v1.model.input.GrupoInputModel;
import com.algafood.algafoodapi.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.algafood.algafoodapi.domain.models.Grupo;
import com.algafood.algafoodapi.domain.repository.GrupoRepository;
import com.algafood.algafoodapi.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("v1/grupos")
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private GrupoModelAssembler grupoModel;

    @Autowired
    private GrupoInputDisassembler grupoInputDisassembler;

    @GetMapping
    public CollectionModel<GrupoModel> listar() {
        return grupoModel.toCollectionModel(grupoRepository.findAll());
    }

    @GetMapping("/{grupoId}")
    public GrupoModel buscar(@PathVariable Long grupoId) {
        return grupoModel.toModel(cadastroGrupo.findOrFail(grupoId));
    }

    @PostMapping
    public ResponseEntity<GrupoModel> add(@RequestBody @Valid GrupoInputModel grupoInput) {
        Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);

        GrupoModel grupoDTO = grupoModel.toModel(cadastroGrupo.salvar(grupo));

        return ResponseEntity.status(HttpStatus.CREATED).body(grupoDTO);
    }

    @PutMapping("/{grupoId}")
    public ResponseEntity<GrupoModel> alterar(@PathVariable Long grupoId,
            @RequestBody @Valid GrupoInputModel grupoInput) {
        Grupo grupoCurrent = cadastroGrupo.findOrFail(grupoId);

        grupoInputDisassembler.copyToDomainObject(grupoInput, grupoCurrent);

        grupoCurrent = cadastroGrupo.salvar(grupoCurrent);

        return ResponseEntity.status(HttpStatus.OK).body(grupoModel.toModel(grupoCurrent));
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        cadastroGrupo.remover(grupoId);
    }
}
