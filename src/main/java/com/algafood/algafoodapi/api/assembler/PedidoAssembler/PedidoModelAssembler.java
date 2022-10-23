package com.algafood.algafoodapi.api.assembler.PedidoAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.InstanceLink;
import com.algafood.algafoodapi.api.controllers.PedidoController;
import com.algafood.algafoodapi.api.model.PedidoModel;
import com.algafood.algafoodapi.domain.models.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private InstanceLink instanceLink;

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);

		pedidoModel.add(instanceLink.linkToPedidos());

		// CLIENTE
		pedidoModel.getCliente().add(instanceLink.linkToUsuarios(pedidoModel.getCliente().getId()));
		pedidoModel.getCliente().add(instanceLink.linkToUsuarios(IanaLinkRelations.COLLECTION_VALUE));

		// RESTAURANTE
		pedidoModel.getRestaurante().add(instanceLink.linkToRestaurantes(pedidoModel.getRestaurante().getId()));
		pedidoModel.getRestaurante().add(instanceLink.linkToRestaurantes(IanaLinkRelations.COLLECTION_VALUE));

		// FORMA DE PAGAMENTO
		pedidoModel.getFormaPagamento()
				.add(instanceLink.linkToFormasPagamentos(pedidoModel.getFormaPagamento().getId()));
		pedidoModel.getFormaPagamento().add(instanceLink.linkToFormasPagamentos(IanaLinkRelations.COLLECTION_VALUE));

		// CIDADE
		pedidoModel.getEndereco().getCidade()
				.add(instanceLink.linkToCidades(pedidoModel.getEndereco().getCidade().getId()));
		pedidoModel.getEndereco().getCidade().add(instanceLink.linkToCidades(IanaLinkRelations.COLLECTION_VALUE));

		return pedidoModel;
	}

	@Override
	public CollectionModel<PedidoModel> toCollectionModel(Iterable<? extends Pedido> entities) {
		return super.toCollectionModel(entities)
				.add(WebMvcLinkBuilder.linkTo(PedidoController.class).withSelfRel());
	}
}
