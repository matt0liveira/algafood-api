package com.algafood.algafoodapi.api.controllers;

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
import com.algafood.algafoodapi.api.assembler.CozinhaAssembler.CozinhaInputDisassembler;
import com.algafood.algafoodapi.api.assembler.CozinhaAssembler.CozinhaModelAssembler;
import com.algafood.algafoodapi.api.model.CozinhaModel;
import com.algafood.algafoodapi.api.model.input.CozinhaInputModel;
import com.algafood.algafoodapi.api.openapi.controller.CozinhaControllerOpenApi;
import com.algafood.algafoodapi.domain.models.Cozinha;
import com.algafood.algafoodapi.domain.repository.CozinhaRepository;
import com.algafood.algafoodapi.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CozinhaModelAssembler cozinhaModelAssembler;

    @Autowired
    private CozinhaInputDisassembler cozinhaInputDisassembler;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        PagedModel<CozinhaModel> cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhasPage,
                cozinhaModelAssembler);

        return cozinhasPagedModel;
    }

    @GetMapping(path = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CozinhaModel buscar(@PathVariable Long cozinhaId) {
        return cozinhaModelAssembler.toModel(cadastroCozinha.findOrFail(cozinhaId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> add(@RequestBody CozinhaInputModel cozinhaInputDTO) {
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInputDTO);
        cozinha = cadastroCozinha.salvar(cozinha);

        return ResponseEntity
                .created(ResourceUriHelper.addUriInResponseHeader(cozinha.getId()))
                .body(cozinhaModelAssembler.toModel(cozinha));
    }

    @PutMapping(path = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CozinhaModel> alterar(@PathVariable Long cozinhaId,
            @RequestBody CozinhaInputModel cozinhaInputDTO) {
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
    public CollectionModel<CozinhaModel> buscarPorNome(@RequestParam("nome") String nome) {
        List<Cozinha> cozinhas = cozinhaRepository.findByNomeContaining(nome);

        return cozinhaModelAssembler.toCollectionModel(cozinhas);
    }

}