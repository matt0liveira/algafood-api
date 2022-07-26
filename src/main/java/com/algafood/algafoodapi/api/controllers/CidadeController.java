package com.algafood.algafoodapi.api.controllers;

import com.algafood.algafoodapi.api.assembler.CidadeAssembler.CidadeInputDisassembler;
import com.algafood.algafoodapi.api.assembler.CidadeAssembler.CidadeModelAssembler;
import com.algafood.algafoodapi.api.model.CidadeDTO;
import com.algafood.algafoodapi.api.model.input.CidadeInputDTO;
import com.algafood.algafoodapi.domain.exceptions.EstadoNotfoundException;
import com.algafood.algafoodapi.domain.exceptions.NegocioException;
import com.algafood.algafoodapi.domain.models.Cidade;
import com.algafood.algafoodapi.domain.repository.CidadeRepository;
import com.algafood.algafoodapi.domain.service.CadastroCidadeService;

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

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("cidades")
public class CidadeController {
    
    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping
    public List<CidadeDTO> listar() {
        List<Cidade> cidades = cidadeRepository.findAll();

        return cidadeModelAssembler.toCollectionDTO(cidades);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInputDTO);
            cidade = cadastroCidade.salvar(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(cidadeModelAssembler.toDTO(cidade));
        } catch (EstadoNotfoundException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<?> atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInputDTO cidadeInputDTO) {

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
