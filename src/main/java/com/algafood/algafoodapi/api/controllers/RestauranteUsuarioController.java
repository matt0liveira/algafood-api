package com.algafood.algafoodapi.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.InstanceLink;
import com.algafood.algafoodapi.api.assembler.UsuarioAssembler.UsuarioModelAssembler;
import com.algafood.algafoodapi.api.model.UsuarioModel;
import com.algafood.algafoodapi.api.openapi.controller.RestauranteUsuarioControllerOpenApi;
import com.algafood.algafoodapi.domain.models.Restaurante;
import com.algafood.algafoodapi.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioController implements RestauranteUsuarioControllerOpenApi {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;

	@Autowired
	private InstanceLink instanceLink;

	@GetMapping
	public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.findOrFail(restauranteId);

		CollectionModel<UsuarioModel> usuariosModel = usuarioModelAssembler
				.toCollectionModel(restaurante.getResponsaveis())
				.removeLinks()
				.add(instanceLink.linkToRestaurantesUsuarios(restaurante.getId(), "responsaveis"))
				.add(instanceLink.linkToAssociacaoRestauranteResponsavel(restauranteId, "associar"));

		usuariosModel.getContent().forEach(usuarioModel -> {
			usuarioModel.add(instanceLink.linkToDesassociacaoRestauranteResponsavel(restaurante.getId(),
					usuarioModel.getId(), "desassociar"));
		});

		return usuariosModel;
	}

	@PutMapping("/{responsavelId}")
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
		cadastroRestaurante.associarResponsavel(restauranteId, responsavelId);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{responsavelId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
		cadastroRestaurante.desassociarResponsavel(restauranteId, responsavelId);

		return ResponseEntity.noContent().build();
	}

}
