package com.algafood.algafoodapi.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.assembler.ProdutoAssembler.ProdutoInputDisassembler;
import com.algafood.algafoodapi.api.assembler.ProdutoAssembler.ProdutoModelAssembler;
import com.algafood.algafoodapi.api.model.ProdutoModel;
import com.algafood.algafoodapi.api.model.input.ProdutoInputModel;
import com.algafood.algafoodapi.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algafood.algafoodapi.domain.models.Produto;
import com.algafood.algafoodapi.domain.models.Restaurante;
import com.algafood.algafoodapi.domain.repository.ProdutoRepository;
import com.algafood.algafoodapi.domain.service.CadastroProdutoService;
import com.algafood.algafoodapi.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    @Autowired
    private ProdutoModelAssembler produtoModel;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoInputDisassembler produtoInputDisassembler;

    @GetMapping
    public List<ProdutoModel> listar(@PathVariable Long restauranteId,
            @RequestParam(required = false) boolean includeInativos) {
        Restaurante restaurante = cadastroRestaurante.findOrFail(restauranteId);
        List<Produto> produtos = null;

        if (includeInativos) {
            produtos = produtoRepository.findAllByRestaurante(restaurante);
        } else {
            produtos = produtoRepository.findAtivosByRestaurante(restaurante);
        }

        return produtoModel.toCollectionDTO(produtos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = cadastroProduto.findOrFail(restauranteId, produtoId);

        return produtoModel.toDTO(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel add(@PathVariable Long restauranteId, @RequestBody ProdutoInputModel produtoInput) {
        Restaurante restaurante = cadastroRestaurante.findOrFail(restauranteId);

        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        produto = cadastroProduto.salvar(produto);

        return produtoModel.toDTO(produto);
    }

    @PutMapping("/{produtoId}")
    public ProdutoModel alterar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
            @RequestBody ProdutoInputModel produtoInput) {
        Produto produtoCurrent = cadastroProduto.findOrFail(restauranteId, produtoId);

        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoCurrent);

        produtoCurrent = cadastroProduto.salvar(produtoCurrent);

        return produtoModel.toDTO(produtoCurrent);
    }

}