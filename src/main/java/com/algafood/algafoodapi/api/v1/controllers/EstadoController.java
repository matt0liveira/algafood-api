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
import com.algafood.algafoodapi.api.v1.assembler.EstadoAssembler.EstadoInputDisassembler;
import com.algafood.algafoodapi.api.v1.assembler.EstadoAssembler.EstadoModelAssembler;
import com.algafood.algafoodapi.api.v1.model.EstadoModel;
import com.algafood.algafoodapi.api.v1.model.input.EstadoInputModel;
import com.algafood.algafoodapi.api.v1.openapi.controller.EstadoControllerOpenApi;
import com.algafood.algafoodapi.core.security.CheckSecurity;
import com.algafood.algafoodapi.domain.models.Estado;
import com.algafood.algafoodapi.domain.repository.EstadoRepository;
import com.algafood.algafoodapi.domain.service.CadastroEstadoService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
@RestController
@RequestMapping(path = "v1/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @CheckSecurity.Estados.PodeConsutar
    @GetMapping
    public CollectionModel<EstadoModel> listar() {
        List<Estado> estados = estadoRepository.findAll();

        return estadoModelAssembler.toCollectionModel(estados);
    }

    @CheckSecurity.Estados.PodeConsutar
    @GetMapping("/{estadoId}")
    public ResponseEntity<EstadoModel> buscar(@PathVariable Long estadoId) {
        return ResponseEntity.ok(estadoModelAssembler.toModel(cadastroEstado.findOrFail(estadoId)));
    }

    @CheckSecurity.Estados.PodeEditar
    @PostMapping
    public ResponseEntity<EstadoModel> add(@RequestBody @Valid EstadoInputModel estadoInputDTO) {
        Estado estado = estadoInputDisassembler.toDomainObject(estadoInputDTO);

        estado = cadastroEstado.salvar(estado);

        return ResponseEntity
                .created(ResourceUriHelper.addUriInResponseHeader(estado.getId()))
                .body(estadoModelAssembler.toModel(estado));
    }

    @CheckSecurity.Estados.PodeEditar
    @PutMapping("/{estadoId}")
    public ResponseEntity<EstadoModel> atualizar(@PathVariable Long estadoId,
            @RequestBody @Valid EstadoInputModel estadoInputDTO) {

        Estado estadoCurrent = cadastroEstado.findOrFail(estadoId);

        estadoInputDisassembler.copyToDomainOject(estadoInputDTO, estadoCurrent);

        estadoCurrent = cadastroEstado.salvar(estadoCurrent);
        return ResponseEntity.ok(estadoModelAssembler.toModel(estadoCurrent));

    }

    @CheckSecurity.Estados.PodeEditar
    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        cadastroEstado.remover(estadoId);
    }

}
