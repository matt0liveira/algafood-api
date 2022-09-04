package com.algafood.algafoodapi.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.assembler.FormaPagamentoAssembler.FormaPagamentoModelAssembler;
import com.algafood.algafoodapi.api.model.FormaPagamentoDTO;
import com.algafood.algafoodapi.domain.models.Restaurante;
import com.algafood.algafoodapi.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {
    
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    // @Autowired
    // private SmartValidator validator;


    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @GetMapping
    public List<FormaPagamentoDTO> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.findOrFail(restauranteId);

        return formaPagamentoModelAssembler.toCollectionDTO(restaurante.getFormasPagamento());
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);
    }

}