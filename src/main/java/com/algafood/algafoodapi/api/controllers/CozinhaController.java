package com.algafood.algafoodapi.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

import com.algafood.algafoodapi.api.assembler.CozinhaAssembler.CozinhaInputDisassembler;
import com.algafood.algafoodapi.api.assembler.CozinhaAssembler.CozinhaModelAssembler;
import com.algafood.algafoodapi.api.model.CozinhaDTO;
import com.algafood.algafoodapi.api.model.input.CozinhaInputDTO;
import com.algafood.algafoodapi.domain.models.Cozinha;
import com.algafood.algafoodapi.domain.repository.CozinhaRepository;
import com.algafood.algafoodapi.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("cozinhas")
public class CozinhaController {
    
    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CozinhaModelAssembler cozinhaModelAssembler;

    @Autowired
    private CozinhaInputDisassembler cozinhaInputDisassembler;

    @GetMapping
    public Page<CozinhaDTO> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        List<CozinhaDTO> cozinhasModel = cozinhaModelAssembler.toCollectionDTO(cozinhasPage.getContent());

        Page<CozinhaDTO> cozinhasModelPage = new PageImpl<>(cozinhasModel, pageable, cozinhasPage.getTotalElements());

        return cozinhasModelPage;
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
       return cozinhaModelAssembler.toDTO(cadastroCozinha.findOrFail(cozinhaId));
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CozinhaInputDTO cozinhaInputDTO) {
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInputDTO);
        cozinha = cadastroCozinha.salvar(cozinha);
        return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaModelAssembler.toDTO(cozinha));
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<CozinhaDTO> alterar(@PathVariable Long cozinhaId, @RequestBody CozinhaInputDTO cozinhaInputDTO) {
        Cozinha cozinhaCurrent = cadastroCozinha.findOrFail(cozinhaId);

        cozinhaInputDisassembler.copyToDomainOject(cozinhaInputDTO, cozinhaCurrent);
        cozinhaCurrent = cadastroCozinha.salvar(cozinhaCurrent);
        return ResponseEntity.ok(cozinhaModelAssembler.toDTO(cozinhaCurrent));
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cadastroCozinha.remover(cozinhaId);
    }

    @GetMapping("/buscar-por-nome")
    public List<Cozinha> buscarPorNome(@RequestParam("nome") String nome) {
        return cozinhaRepository.findByNomeContaining(nome);
    }

}