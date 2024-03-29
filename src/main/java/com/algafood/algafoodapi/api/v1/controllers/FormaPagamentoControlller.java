package com.algafood.algafoodapi.api.v1.controllers;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
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
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algafood.algafoodapi.api.ResourceUriHelper;
import com.algafood.algafoodapi.api.v1.assembler.FormaPagamentoAssembler.FormaPagamentoInputDisassembler;
import com.algafood.algafoodapi.api.v1.assembler.FormaPagamentoAssembler.FormaPagamentoModelAssembler;
import com.algafood.algafoodapi.api.v1.model.FormaPagamentoModel;
import com.algafood.algafoodapi.api.v1.model.input.FormaPagamentoInputDTO;
import com.algafood.algafoodapi.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algafood.algafoodapi.core.security.CheckSecurity;
import com.algafood.algafoodapi.domain.exceptions.FormaPagamentoNotfoundException;
import com.algafood.algafoodapi.domain.exceptions.NegocioException;
import com.algafood.algafoodapi.domain.models.FormaPagamento;
import com.algafood.algafoodapi.domain.repository.FormaPagamentoRepository;
import com.algafood.algafoodapi.domain.service.CadastroFormaPagamentoService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
@RestController
@RequestMapping(path = "v1/formas-pagamentos", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoControlller implements FormaPagamentoControllerOpenApi {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    @CheckSecurity.FormasPagamentos.PodeConsutar
    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest req) {
        ShallowEtagHeaderFilter.disableContentCaching(req.getRequest());

        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualiazacao();

        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if (req.checkNotModified(eTag)) {
            return null;
        }

        List<FormaPagamento> formasPagamentos = formaPagamentoRepository.findAll();

        CollectionModel<FormaPagamentoModel> formasPagamentosModel = formaPagamentoModelAssembler
                .toCollectionModel(formasPagamentos);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formasPagamentosModel);
    }

    @CheckSecurity.FormasPagamentos.PodeConsutar
    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long formaPagamentoId) {
        FormaPagamentoModel formaPagamentoModel = formaPagamentoModelAssembler
                .toModel(cadastroFormaPagamentoService.findOrFail(formaPagamentoId));

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(15, TimeUnit.SECONDS).cachePublic())
                .body(formaPagamentoModel);
    }

    @CheckSecurity.FormasPagamentos.PodeEditar
    @PostMapping
    public ResponseEntity<FormaPagamentoModel> add(@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO) {
        try {
            FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInputDTO);

            formaPagamento = cadastroFormaPagamentoService.salvar(formaPagamento);

            return ResponseEntity.created(ResourceUriHelper
                    .addUriInResponseHeader(formaPagamento.getId()))
                    .body(formaPagamentoModelAssembler.toModel(formaPagamento));
        } catch (FormaPagamentoNotfoundException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.FormasPagamentos.PodeEditar
    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoModel> alterar(@PathVariable Long formaPagamentoId,
            @RequestBody FormaPagamentoInputDTO formaPagamentoInputDTO) {
        try {
            FormaPagamento formaPagamentoCurrent = cadastroFormaPagamentoService.findOrFail(formaPagamentoId);

            formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInputDTO, formaPagamentoCurrent);

            formaPagamentoCurrent = cadastroFormaPagamentoService.salvar(formaPagamentoCurrent);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(formaPagamentoModelAssembler.toModel(formaPagamentoCurrent));
        } catch (FormaPagamentoNotfoundException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.FormasPagamentos.PodeEditar
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long formaPagamentoId) {
        cadastroFormaPagamentoService.excluir(formaPagamentoId);
    }
}
