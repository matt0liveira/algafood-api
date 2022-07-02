package com.algafood.algafoodapi.api.controllers;

import java.util.List;

import javax.validation.Valid;

import com.algafood.algafoodapi.domain.models.Estado;
import com.algafood.algafoodapi.domain.repository.EstadoRepository;
import com.algafood.algafoodapi.domain.service.CadastroEstadoService;

import org.springframework.beans.BeanUtils;
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

@RestController
@RequestMapping("estados")
public class EstadoController {
    
    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid Estado estado) {
        cadastroEstado.salvar(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(estado);
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<?> atualizar(@PathVariable Long estadoId, @RequestBody @Valid Estado estado) {

        Estado estadoCurrent = cadastroEstado.findOrFail(estadoId);

        BeanUtils.copyProperties(estado, estadoCurrent, "id");
        cadastroEstado.salvar(estadoCurrent);
        return ResponseEntity.ok(estadoCurrent);

    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        cadastroEstado.remover(estadoId);
    }

}
