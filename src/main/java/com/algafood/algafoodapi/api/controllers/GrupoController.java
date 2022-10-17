package com.algafood.algafoodapi.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.algafood.algafoodapi.api.assembler.GrupoAssembler.GrupoInputDisassembler;
import com.algafood.algafoodapi.api.assembler.GrupoAssembler.GrupoModelAssembler;
import com.algafood.algafoodapi.api.controllers.openapi.GrupoControllerOpenApi;
import com.algafood.algafoodapi.api.model.GrupoDTO;
import com.algafood.algafoodapi.api.model.input.GrupoInputDTO;
import com.algafood.algafoodapi.domain.models.Grupo;
import com.algafood.algafoodapi.domain.repository.GrupoRepository;
import com.algafood.algafoodapi.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
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
    public List<GrupoDTO> listar() {
        return grupoModel.toCollectionDTO(grupoRepository.findAll());
    }

    @GetMapping("/{grupoId}")
    public GrupoDTO buscar(@PathVariable Long grupoId) {
        return grupoModel.toDTO(cadastroGrupo.findOrFail(grupoId));
    }

    @PostMapping
    public ResponseEntity<GrupoDTO> add(@RequestBody @Valid GrupoInputDTO grupoInput) {
        Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);

        GrupoDTO grupoDTO = grupoModel.toDTO(cadastroGrupo.salvar(grupo));

        return ResponseEntity.status(HttpStatus.CREATED).body(grupoDTO);
    }

    @PutMapping("/{grupoId}")
    public ResponseEntity<GrupoDTO> alterar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInputDTO grupoInput) {
        Grupo grupoCurrent = cadastroGrupo.findOrFail(grupoId);

        grupoInputDisassembler.copyToDomainObject(grupoInput, grupoCurrent);

        grupoCurrent = cadastroGrupo.salvar(grupoCurrent);

        return ResponseEntity.status(HttpStatus.OK).body(grupoModel.toDTO(grupoCurrent));
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        cadastroGrupo.remover(grupoId);
    }
}
