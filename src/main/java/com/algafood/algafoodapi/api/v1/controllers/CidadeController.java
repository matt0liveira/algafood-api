package com.algafood.algafoodapi.api.v1.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.algafood.algafoodapi.api.ResourceUriHelper;
import com.algafood.algafoodapi.api.v1.assembler.CidadeAssembler.CidadeInputDisassembler;
import com.algafood.algafoodapi.api.v1.assembler.CidadeAssembler.CidadeModelAssembler;
import com.algafood.algafoodapi.api.v1.model.CidadeModel;
import com.algafood.algafoodapi.api.v1.model.input.CidadeInputModel;
import com.algafood.algafoodapi.api.v1.openapi.controller.CidadeControllerOpenApi;
// import com.algafood.algafoodapi.core.web.MediaTypesWeb;
import com.algafood.algafoodapi.domain.exceptions.EstadoNotfoundException;
import com.algafood.algafoodapi.domain.exceptions.NegocioException;
import com.algafood.algafoodapi.domain.models.Cidade;
import com.algafood.algafoodapi.domain.repository.CidadeRepository;
import com.algafood.algafoodapi.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path = "v1/cidades")
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @Deprecated
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<CidadeModel> listar() {
        List<Cidade> cidades = cidadeRepository.findAll();

        return cidadeModelAssembler.toCollectionModel(cidades);
    }

    @Deprecated
    @Override
    @GetMapping(path = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CidadeModel> buscar(@PathVariable Long cidadeId) {
        CidadeModel cidadeModel = cidadeModelAssembler.toModel(cadastroCidade.findOrFail(cidadeId));

        return ResponseEntity.ok(cidadeModel);
    }

    @Deprecated
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> add(
            @RequestBody @Valid CidadeInputModel cidadeInputDTO) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInputDTO);
            cidade = cadastroCidade.salvar(cidade);

            return ResponseEntity
                    .created(ResourceUriHelper.addUriInResponseHeader(cidade.getId()))
                    .body(cidadeModelAssembler.toModel(cidade));
        } catch (EstadoNotfoundException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Deprecated
    @PutMapping(path = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> atualizar(@PathVariable Long cidadeId,
            @RequestBody @Valid CidadeInputModel cidadeInputDTO) {

        try {
            Cidade cidadeCurrent = cadastroCidade.findOrFail(cidadeId);

            cidadeInputDisassembler.copyToDomainOject(cidadeInputDTO, cidadeCurrent);

            cidadeCurrent = cadastroCidade.salvar(cidadeCurrent);

            return ResponseEntity.ok(cidadeModelAssembler.toModel(cidadeCurrent));
        } catch (EstadoNotfoundException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @Deprecated
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidade.remover(cidadeId);
    }

}
