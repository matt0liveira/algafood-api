package com.algafood.algafoodapi.api.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.v1.InstanceLink;
import com.algafood.algafoodapi.api.v1.assembler.FormaPagamentoAssembler.FormaPagamentoModelAssembler;
import com.algafood.algafoodapi.api.v1.model.FormaPagamentoModel;
import com.algafood.algafoodapi.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.algafood.algafoodapi.domain.models.Restaurante;
import com.algafood.algafoodapi.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "v1/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    // @Autowired
    // private SmartValidator validator;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private InstanceLink instanceLink;

    @GetMapping
    public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.findOrFail(restauranteId);

        CollectionModel<FormaPagamentoModel> formasPagamentosModel = formaPagamentoModelAssembler
                .toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks()
                .add(instanceLink.linkToRestaurantesFormasPagamento(restauranteId, "formas-pagamentos"))
                .add(instanceLink.linkToAssociacaoFormaPagamento(restauranteId, "associar"));

        formasPagamentosModel.getContent().forEach(formaPagamentoModel -> {
            formaPagamentoModel.add(instanceLink.linkToDesassociacaoFormaPagamento(restaurante.getId(),
                    formaPagamentoModel.getId(), "desassociar"));
        });

        return formasPagamentosModel;
    }

    @DeleteMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }

}