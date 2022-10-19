package com.algafood.algafoodapi.api.controllers;

import java.util.List;

import javax.validation.Valid;

import com.algafood.algafoodapi.api.assembler.EstadoAssembler.EstadoInputDisassembler;
import com.algafood.algafoodapi.api.assembler.EstadoAssembler.EstadoModelAssembler;
import com.algafood.algafoodapi.api.model.EstadoDTO;
import com.algafood.algafoodapi.api.model.input.EstadoInputDTO;
import com.algafood.algafoodapi.api.openapi.controller.EstadoControllerOpenApi;
import com.algafood.algafoodapi.domain.models.Estado;
import com.algafood.algafoodapi.domain.repository.EstadoRepository;
import com.algafood.algafoodapi.domain.service.CadastroEstadoService;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping(path = "estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @GetMapping
    public List<EstadoDTO> listar() {
        List<Estado> estados = estadoRepository.findAll();

        return estadoModelAssembler.toCollectionDTO(estados);
    }

    @PostMapping
    public ResponseEntity<EstadoDTO> add(@RequestBody @Valid EstadoInputDTO estadoInputDTO) {
        Estado estado = estadoInputDisassembler.toDomainObject(estadoInputDTO);

        estado = cadastroEstado.salvar(estado);

        return ResponseEntity.status(HttpStatus.CREATED).body(estadoModelAssembler.toDTO(estado));
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<EstadoDTO> atualizar(@PathVariable Long estadoId,
            @RequestBody @Valid EstadoInputDTO estadoInputDTO) {

        Estado estadoCurrent = cadastroEstado.findOrFail(estadoId);

        estadoInputDisassembler.copyToDomainOject(estadoInputDTO, estadoCurrent);

        estadoCurrent = cadastroEstado.salvar(estadoCurrent);
        return ResponseEntity.ok(estadoModelAssembler.toDTO(estadoCurrent));

    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        cadastroEstado.remover(estadoId);
    }

}
