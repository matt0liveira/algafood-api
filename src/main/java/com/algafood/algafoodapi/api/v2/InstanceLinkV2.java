package com.algafood.algafoodapi.api.v2;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.v1.controllers.CidadeController;
import com.algafood.algafoodapi.api.v2.controllers.CozinhaControllerV2;

@Component
public class InstanceLinkV2 {
	public Link linkToCidades(String rel) {
		return WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder
						.methodOn(CidadeController.class)
						.listar())
				.withRel(rel);
	}

	public Link linkToCidades() {
		return linkToCidades(IanaLinkRelations.SELF_VALUE);
	}

	public Link linkToCozinhas(String rel) {
		return WebMvcLinkBuilder.linkTo(CozinhaControllerV2.class).withRel(rel);
	}

	public Link linkToCozinhas() {
		return linkToCozinhas(IanaLinkRelations.SELF_VALUE);
	}
}