package com.algafood.algafoodapi.api.v1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
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

import com.algafood.algafoodapi.api.v1.InstanceLink;
import com.algafood.algafoodapi.api.v1.assembler.ProdutoAssembler.ProdutoInputDisassembler;
import com.algafood.algafoodapi.api.v1.assembler.ProdutoAssembler.ProdutoModelAssembler;
import com.algafood.algafoodapi.api.v1.model.ProdutoModel;
import com.algafood.algafoodapi.api.v1.model.input.ProdutoInputModel;
import com.algafood.algafoodapi.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algafood.algafoodapi.core.security.CheckSecurity;
import com.algafood.algafoodapi.domain.models.Produto;
import com.algafood.algafoodapi.domain.models.Restaurante;
import com.algafood.algafoodapi.domain.repository.ProdutoRepository;
import com.algafood.algafoodapi.domain.service.CadastroProdutoService;
import com.algafood.algafoodapi.domain.service.CadastroRestauranteService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
@RestController
@RequestMapping(path = "v1/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @Autowired
    private InstanceLink instanceLink;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public CollectionModel<ProdutoModel> listar(@PathVariable Long restauranteId,
            @RequestParam(required = false) Boolean includeInativos) {
        Restaurante restaurante = cadastroRestaurante.findOrFail(restauranteId);
        List<Produto> produtos = null;

        if (includeInativos) {
            produtos = produtoRepository.findAllByRestaurante(restaurante);
        } else {
            produtos = produtoRepository.findAtivosByRestaurante(restaurante);
        }

        return produtoModel.toCollectionModel(produtos)
                .add(instanceLink.linkToProdutos(restaurante.getId(), IanaLinkRelations.COLLECTION_VALUE));
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = cadastroProduto.findOrFail(restauranteId, produtoId);

        return produtoModel.toModel(produto);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel add(@PathVariable Long restauranteId, @RequestBody ProdutoInputModel produtoInput) {
        Restaurante restaurante = cadastroRestaurante.findOrFail(restauranteId);

        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        produto = cadastroProduto.salvar(produto);

        return produtoModel.toModel(produto);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{produtoId}")
    public ProdutoModel alterar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
            @RequestBody ProdutoInputModel produtoInput) {
        Produto produtoCurrent = cadastroProduto.findOrFail(restauranteId, produtoId);

        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoCurrent);

        produtoCurrent = cadastroProduto.salvar(produtoCurrent);

        return produtoModel.toModel(produtoCurrent);
    }

}