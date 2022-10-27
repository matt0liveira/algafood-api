package com.algafood.algafoodapi.api.v2.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.ResourceUriHelper;
import com.algafood.algafoodapi.api.v2.assembler.CidadeAssemblerV2.CidadeInputDisassemblerV2;
import com.algafood.algafoodapi.api.v2.assembler.CidadeAssemblerV2.CidadeModelAssemblerV2;
import com.algafood.algafoodapi.api.v2.model.CidadeModelV2;
import com.algafood.algafoodapi.api.v2.model.input.CidadeInputModelV2;
import com.algafood.algafoodapi.api.v2.openapi.controller.CidadeControllerOpenApiV2;
// import com.algafood.algafoodapi.core.web.MediaTypesWeb;
import com.algafood.algafoodapi.domain.exceptions.EstadoNotfoundException;
import com.algafood.algafoodapi.domain.exceptions.NegocioException;
import com.algafood.algafoodapi.domain.models.Cidade;
import com.algafood.algafoodapi.domain.repository.CidadeRepository;
import com.algafood.algafoodapi.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path = "v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 implements CidadeControllerOpenApiV2 {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeModelAssemblerV2 cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassemblerV2 cidadeInputDisassembler;

    @GetMapping
    public CollectionModel<CidadeModelV2> listar() {
        List<Cidade> cidades = cidadeRepository.findAll();

        return cidadeModelAssembler.toCollectionModel(cidades);
    }

    @GetMapping(path = "/{cidadeId}")
    public ResponseEntity<CidadeModelV2> buscar(@PathVariable Long cidadeId) {
        CidadeModelV2 cidadeModel = cidadeModelAssembler.toModel(cadastroCidade.findOrFail(cidadeId));

        return ResponseEntity.ok(cidadeModel);
    }

    @PostMapping
    public ResponseEntity<?> add(
            @RequestBody @Valid CidadeInputModelV2 cidadeInputDTO) {
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

    @PutMapping(path = "/{cidadeId}")
    public ResponseEntity<?> atualizar(@PathVariable Long cidadeId,
            @RequestBody @Valid CidadeInputModelV2 cidadeInputDTO) {

        try {
            Cidade cidadeCurrent = cadastroCidade.findOrFail(cidadeId);

            cidadeInputDisassembler.copyToDomainOject(cidadeInputDTO, cidadeCurrent);

            cidadeCurrent = cadastroCidade.salvar(cidadeCurrent);

            return ResponseEntity.ok(cidadeModelAssembler.toModel(cidadeCurrent));
        } catch (EstadoNotfoundException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    // @DeleteMapping("/{cidadeId}")
    // @ResponseStatus(HttpStatus.NO_CONTENT)
    // public void remover(@PathVariable Long cidadeId) {
    // cadastroCidade.remover(cidadeId);
    // }

}
