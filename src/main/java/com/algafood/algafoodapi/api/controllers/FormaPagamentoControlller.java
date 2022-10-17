package com.algafood.algafoodapi.api.controllers;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
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
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algafood.algafoodapi.api.assembler.FormaPagamentoAssembler.FormaPagamentoInputDisassembler;
import com.algafood.algafoodapi.api.assembler.FormaPagamentoAssembler.FormaPagamentoModelAssembler;
import com.algafood.algafoodapi.api.model.FormaPagamentoDTO;
import com.algafood.algafoodapi.api.model.input.FormaPagamentoInputDTO;
import com.algafood.algafoodapi.domain.exceptions.FormaPagamentoNotfoundException;
import com.algafood.algafoodapi.domain.exceptions.NegocioException;
import com.algafood.algafoodapi.domain.models.FormaPagamento;
import com.algafood.algafoodapi.domain.repository.FormaPagamentoRepository;
import com.algafood.algafoodapi.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("formas-pagamentos")
public class FormaPagamentoControlller {
    
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> listar(ServletWebRequest req) {
        ShallowEtagHeaderFilter.disableContentCaching(req.getRequest());

        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualiazacao();

        if(dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if(req.checkNotModified(eTag)) {
            return null;
        }

        List<FormaPagamento> formasPagamentos = formaPagamentoRepository.findAll();

        List<FormaPagamentoDTO> formasPagamentosModel = formaPagamentoModelAssembler.toCollectionDTO(formasPagamentos);

        return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
            .eTag(eTag)
            .body(formasPagamentosModel);
    }

    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long formaPagamentoId) {
        FormaPagamentoDTO formaPagamentoModel = formaPagamentoModelAssembler.toDTO(cadastroFormaPagamentoService.findOrFail(formaPagamentoId));

        return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(15, TimeUnit.SECONDS).cachePublic())
            .body(formaPagamentoModel);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO) {
        try {
            FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInputDTO);

            formaPagamento = cadastroFormaPagamentoService.salvar(formaPagamento);

            return ResponseEntity.status(HttpStatus.CREATED).body(formaPagamentoModelAssembler.toDTO(formaPagamento));
        } catch(FormaPagamentoNotfoundException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<?> alterar(@PathVariable Long formaPagamentoId, @RequestBody FormaPagamentoInputDTO formaPagamentoInputDTO) {
        try {
            FormaPagamento formaPagamentoCurrent = cadastroFormaPagamentoService.findOrFail(formaPagamentoId);

            formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInputDTO, formaPagamentoCurrent);

            formaPagamentoCurrent = cadastroFormaPagamentoService.salvar(formaPagamentoCurrent);

            return ResponseEntity.status(HttpStatus.OK).body(formaPagamentoModelAssembler.toDTO(formaPagamentoCurrent));
        } catch(FormaPagamentoNotfoundException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long formaPagamentoId) {
        cadastroFormaPagamentoService.excluir(formaPagamentoId);
    }
}
