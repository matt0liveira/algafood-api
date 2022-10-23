package com.algafood.algafoodapi.api;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.controllers.CidadeController;
import com.algafood.algafoodapi.api.controllers.CozinhaController;
import com.algafood.algafoodapi.api.controllers.EstadoController;
import com.algafood.algafoodapi.api.controllers.FormaPagamentoControlller;
import com.algafood.algafoodapi.api.controllers.PedidoController;
import com.algafood.algafoodapi.api.controllers.RestauranteController;
import com.algafood.algafoodapi.api.controllers.RestauranteUsuarioController;
import com.algafood.algafoodapi.api.controllers.UsuarioController;
import com.algafood.algafoodapi.api.controllers.UsuarioGrupoController;

@Component
public class InstanceLink {

    public static final TemplateVariables PAGINATION_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", VariableType.REQUEST_PARAM),
            new TemplateVariable("size", VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", VariableType.REQUEST_PARAM));

    public Link linkToPedidos() {
        TemplateVariables filterVariables = new TemplateVariables(
                new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));

        return Link.of(
                UriTemplate.of(WebMvcLinkBuilder
                        .linkTo(PedidoController.class)
                        .toUri()
                        .toString(),
                        PAGINATION_VARIABLES.concat(filterVariables)),
                "pedidos");
    }

    public Link linkToUsuarios(Long usuarioId) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder
                        .methodOn(UsuarioController.class)
                        .buscar(usuarioId))
                .withSelfRel();
    }

    public Link linkToUsuarios(String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder
                        .methodOn(UsuarioController.class)
                        .listar())
                .withRel(rel);
    }

    public Link linkToUsuariosGrupos(Long usuarioId, String rel) {
        return WebMvcLinkBuilder
                .linkTo(
                        WebMvcLinkBuilder
                                .methodOn(UsuarioGrupoController.class)
                                .listar(usuarioId))
                .withRel(rel);
    }

    public Link linkToRestaurantes(Long restauranteId) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder
                        .methodOn(RestauranteController.class)
                        .buscar(restauranteId))
                .withSelfRel();
    }

    public Link linkToRestaurantes(String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder
                        .methodOn(RestauranteController.class)
                        .listar(""))
                .withRel(rel);
    }

    public Link linkToFormasPagamentos(Long formaPagamentoId) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder
                        .methodOn(FormaPagamentoControlller.class)
                        .buscar(formaPagamentoId))
                .withSelfRel();
    }

    public Link linkToFormasPagamentos(String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder
                        .methodOn(FormaPagamentoControlller.class)
                        .listar(null))
                .withRel(rel);
    }

    public Link linkToCidades(Long cidadeId) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder
                        .methodOn(CidadeController.class)
                        .buscar(cidadeId))
                .withSelfRel();
    }

    public Link linkToCidades(String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder
                        .methodOn(CidadeController.class)
                        .listar())
                .withRel(rel);
    }

    public Link linkToCidades() {
        return WebMvcLinkBuilder.linkTo(CozinhaController.class).withSelfRel();
    }

    public Link linkToEstados(String rel) {
        return WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EstadoController.class)
                        .listar())
                .withRel(rel);
    }

    public Link linkToEstados(Long estadoId) {
        return WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EstadoController.class)
                        .buscar(estadoId))
                .withSelfRel();
    }

    public Link linkToRestaurantesUsuarios(Long restauranteId) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder
                        .methodOn(RestauranteUsuarioController.class)
                        .listar(restauranteId))
                .withSelfRel();
    }
}
