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
import com.algafood.algafoodapi.api.controllers.FluxoPedidoController;
import com.algafood.algafoodapi.api.controllers.FormaPagamentoControlller;
import com.algafood.algafoodapi.api.controllers.PedidoController;
import com.algafood.algafoodapi.api.controllers.RestauranteController;
import com.algafood.algafoodapi.api.controllers.RestauranteFormaPagamentoController;
import com.algafood.algafoodapi.api.controllers.RestauranteUsuarioController;
import com.algafood.algafoodapi.api.controllers.UsuarioController;
import com.algafood.algafoodapi.api.controllers.UsuarioGrupoController;

@Component
public class InstanceLink {

	public static final TemplateVariables PAGINATION_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));

	public Link linkToPedidos(String rel) {
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
				rel);
	}

	public Link linkToRestaurantes(String rel) {
		TemplateVariables projecaoVariables = new TemplateVariables(
				new TemplateVariable("projecao", VariableType.REQUEST_PARAM));

		return Link.of(
				UriTemplate.of(WebMvcLinkBuilder
						.linkTo(RestauranteController.class)
						.toUri()
						.toString(),
						projecaoVariables),
				rel);
	}

	public Link linkToUsuario(Long usuarioId) {
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

	public Link linkToRestaurante(Long restauranteId) {
		return WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder
						.methodOn(RestauranteController.class)
						.buscar(restauranteId))
				.withSelfRel();
	}

	public Link linkToFormasPagamento(Long formaPagamentoId) {
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

	public Link linkToCidade(Long cidadeId) {
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

	public Link linkToCozinhas() {
		return WebMvcLinkBuilder.linkTo(CozinhaController.class).withSelfRel();
	}

	public Link linkToCozinha(Long cozinhaId) {
		return WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder
						.methodOn(CozinhaController.class)
						.buscar(cozinhaId))
				.withSelfRel();
	}

	public Link linkToEstados(String rel) {
		return WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EstadoController.class)
						.listar())
				.withRel(rel);
	}

	public Link linkToEstado(Long estadoId) {
		return WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EstadoController.class)
						.buscar(estadoId))
				.withSelfRel();
	}

	public Link linkToRestaurantesUsuarios(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder
						.methodOn(RestauranteUsuarioController.class)
						.listar(restauranteId))
				.withRel(rel);
	}

	public Link linkToRestaurantesFormasPagamento(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder
						.methodOn(RestauranteFormaPagamentoController.class)
						.listar(restauranteId))
				.withRel(rel);
	}

	public Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
		return WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder
						.methodOn(FluxoPedidoController.class)
						.confirmar(codigoPedido))
				.withRel(rel);
	}

	public Link linkToEntregaPedido(String codigoPedido, String rel) {
		return WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder
						.methodOn(FluxoPedidoController.class)
						.entregar(codigoPedido))
				.withRel(rel);
	}

	public Link linkToCancelamentoPedido(String codigoPedido, String rel) {
		return WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder
						.methodOn(FluxoPedidoController.class)
						.cancelar(codigoPedido))
				.withRel(rel);
	}

	public Link linkToAtivacaoRestaurante(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder
						.methodOn(RestauranteController.class)
						.ativar(restauranteId))
				.withRel(rel);
	}

	public Link linkToInativacaoRestaurante(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder
						.methodOn(RestauranteController.class)
						.inativar(restauranteId))
				.withRel(rel);
	}

	public Link linkToAberturaRestaurante(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder
						.methodOn(RestauranteController.class)
						.abrir(restauranteId))
				.withRel(rel);
	}

	public Link linkToFechamentoestaurante(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder
						.methodOn(RestauranteController.class)
						.fechar(restauranteId))
				.withRel(rel);
	}
}