package com.algafood.algafoodapi.api.v2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
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
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.ResourceUriHelper;
import com.algafood.algafoodapi.api.v2.assembler.CozinhaAssemblerV2.CozinhaInputDisassemblerV2;
import com.algafood.algafoodapi.api.v2.assembler.CozinhaAssemblerV2.CozinhaModelAssemblerV2;
import com.algafood.algafoodapi.api.v2.model.CozinhaModelV2;
import com.algafood.algafoodapi.api.v2.model.input.CozinhaInputModelV2;
import com.algafood.algafoodapi.api.v2.openapi.controller.CozinhaControllerOpenApiV2;
import com.algafood.algafoodapi.domain.models.Cozinha;
import com.algafood.algafoodapi.domain.repository.CozinhaRepository;
import com.algafood.algafoodapi.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("v2/cozinhas")
public class CozinhaControllerV2 implements CozinhaControllerOpenApiV2 {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CozinhaModelAssemblerV2 cozinhaModelAssembler;

    @Autowired
    private CozinhaInputDisassemblerV2 cozinhaInputDisassembler;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedModel<CozinhaModelV2> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        PagedModel<CozinhaModelV2> cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhasPage,
                cozinhaModelAssembler);

        return cozinhasPagedModel;
    }

    @GetMapping(path = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CozinhaModelV2 buscar(@PathVariable Long cozinhaId) {
        return cozinhaModelAssembler.toModel(cadastroCozinha.findOrFail(cozinhaId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> add(@RequestBody CozinhaInputModelV2 cozinhaInputDTO) {
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInputDTO);
        cozinha = cadastroCozinha.salvar(cozinha);

        return ResponseEntity
                .created(ResourceUriHelper.addUriInResponseHeader(cozinha.getId()))
                .body(cozinhaModelAssembler.toModel(cozinha));
    }

    @PutMapping(path = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CozinhaModelV2> alterar(@PathVariable Long cozinhaId,
            @RequestBody CozinhaInputModelV2 cozinhaInputDTO) {
        Cozinha cozinhaCurrent = cadastroCozinha.findOrFail(cozinhaId);

        cozinhaInputDisassembler.copyToDomainOject(cozinhaInputDTO, cozinhaCurrent);
        cozinhaCurrent = cadastroCozinha.salvar(cozinhaCurrent);
        return ResponseEntity.ok(cozinhaModelAssembler.toModel(cozinhaCurrent));
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cadastroCozinha.remover(cozinhaId);
    }

    @GetMapping("/buscar-por-nome")
    public CollectionModel<CozinhaModelV2> buscarPorNome(@RequestParam("nome") String nome) {
        List<Cozinha> cozinhas = cozinhaRepository.findByNomeContaining(nome);

        return cozinhaModelAssembler.toCollectionModel(cozinhas);
    }

}