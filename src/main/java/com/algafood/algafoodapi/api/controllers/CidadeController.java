package com.algafood.algafoodapi.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
// import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import com.algafood.algafoodapi.api.assembler.CidadeAssembler.CidadeInputDisassembler;
import com.algafood.algafoodapi.api.assembler.CidadeAssembler.CidadeModelAssembler;
import com.algafood.algafoodapi.api.model.CidadeDTO;
import com.algafood.algafoodapi.api.model.input.CidadeInputDTO;
import com.algafood.algafoodapi.api.openapi.controller.CidadeControllerOpenApi;
import com.algafood.algafoodapi.domain.exceptions.EstadoNotfoundException;
import com.algafood.algafoodapi.domain.exceptions.NegocioException;
import com.algafood.algafoodapi.domain.models.Cidade;
import com.algafood.algafoodapi.domain.repository.CidadeRepository;
import com.algafood.algafoodapi.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path = "cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping
    public CollectionModel<CidadeDTO> listar() {
        List<Cidade> cidades = cidadeRepository.findAll();

        List<CidadeDTO> cidadesModel = cidadeModelAssembler.toCollectionDTO(cidades);

        CollectionModel<CidadeDTO> cidadesCollectionModel = CollectionModel.of(cidadesModel);

        return cidadesCollectionModel;
    }

    @Override
    @GetMapping("/{cidadeId}")
    public ResponseEntity<CidadeDTO> buscar(@PathVariable Long cidadeId) {
        CidadeDTO cidadeModel = cidadeModelAssembler.toDTO(cadastroCidade.findOrFail(cidadeId));

        // HARD CODE -> NÃO RECOMENDADO
        // cidadeModel.add(Link.of("http://api.algafood.local:8080/cidades/1"));

        // MONTANDO LINK DE FORMA DINÂMICA COM SLASH
        // cidadeModel.add(WebMvcLinkBuilder
        // .linkTo(CidadeController.class)
        // .slash(cidadeModel.getId())
        // .withSelfRel());

        // MONTANDO LINK DE FORMA DINÂMICA APONTANDO PARA MÉTODO
        cidadeModel.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(CidadeController.class)
                        .buscar(cidadeModel.getId()))
                .withSelfRel());

        // HARD CODE -> NÃO RECOMENDADO
        // cidadeModel.add(Link.of("http://api.algafood.local:8080/cidades",
        // IanaLinkRelations.COLLECTION));

        // MONTANDO LINK DE FORMA DINÂMICA COM SLASH
        // cidadeModel.add(WebMvcLinkBuilder
        // .linkTo(CidadeController.class)
        // .withRel(IanaLinkRelations.COLLECTION));

        // MONTANDO LINK DE FORMA DINÂMICA APONTANDO PARA MÉTODO
        cidadeModel.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(CidadeController.class)
                        .listar())
                .withRel(IanaLinkRelations.COLLECTION));

        // HARD CODE -> NÃO RECOMENDADO
        // cidadeModel.getEstado().add(Link.of("http://api.algafood.local:8080/estados/1"));

        // MONTANDO LINK DE FORMA DINÂMICA COM SLASH
        // cidadeModel.getEstado().add(WebMvcLinkBuilder
        // .linkTo(EstadoController.class)
        // .slash(cidadeModel.getEstado().getId())
        // .withSelfRel());

        // MONTANDO LINK DE FORMA DINÂMICA APONTANDO PARA MÉTODO
        cidadeModel.getEstado().add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EstadoController.class)
                        .buscar(cidadeModel.getEstado().getId()))
                .withSelfRel());

        return ResponseEntity.ok(cidadeModel);
    }

    @PostMapping
    public ResponseEntity<?> add(
            @RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInputDTO);
            cidade = cadastroCidade.salvar(cidade);

            return ResponseEntity
                    .created(ResourceUriHelper.addUriInResponseHeader(cidade.getId()))
                    .body(cidadeModelAssembler.toDTO(cidade));
        } catch (EstadoNotfoundException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<?> atualizar(@PathVariable Long cidadeId,
            @RequestBody @Valid CidadeInputDTO cidadeInputDTO) {

        try {
            Cidade cidadeCurrent = cadastroCidade.findOrFail(cidadeId);

            cidadeInputDisassembler.copyToDomainOject(cidadeInputDTO, cidadeCurrent);

            cidadeCurrent = cadastroCidade.salvar(cidadeCurrent);

            return ResponseEntity.ok(cidadeModelAssembler.toDTO(cidadeCurrent));
        } catch (EstadoNotfoundException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidade.remover(cidadeId);
    }

}
