package com.algafood.algafoodapi.api.v1.assembler.PedidoAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.v1.InstanceLink;
import com.algafood.algafoodapi.api.v1.controllers.PedidoController;
import com.algafood.algafoodapi.api.v1.model.PedidoResumoModel;
import com.algafood.algafoodapi.core.security.SecurityUtils;
import com.algafood.algafoodapi.domain.models.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private InstanceLink instanceLink;

	@Autowired
	private SecurityUtils securityUtils;

	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoModel.class);
	}

	public PedidoResumoModel toModel(Pedido pedido) {
		PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoResumoModel);

		// RESTAURANTE
		if (securityUtils.podeConsultarRestaurantes()) {
			pedidoResumoModel.getRestaurante()
					.add(instanceLink
							.linkToRestaurante(pedidoResumoModel.getRestaurante().getId()));
			pedidoResumoModel.getRestaurante()
					.add(instanceLink.linkToRestaurantes(IanaLinkRelations.COLLECTION_VALUE));
		}

		// CLIENTE
		if (securityUtils.podeConsultarUsuariosGruposPermissoes()) {
			pedidoResumoModel.getCliente().add(instanceLink.linkToUsuario(pedidoResumoModel.getCliente().getId()));
			pedidoResumoModel.getCliente().add(instanceLink.linkToUsuarios(IanaLinkRelations.COLLECTION_VALUE));
		}

		return pedidoResumoModel;
	}

	@Override
	public CollectionModel<PedidoResumoModel> toCollectionModel(Iterable<? extends Pedido> entities) {
		return super.toCollectionModel(entities)
				.add(WebMvcLinkBuilder.linkTo(PedidoController.class).withSelfRel());
	}
}
