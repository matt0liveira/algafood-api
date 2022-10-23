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
import com.algafood.algafoodapi.api.model.PedidoResumoModel;
import com.algafood.algafoodapi.domain.models.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

        @Autowired
        private ModelMapper modelMapper;

        @Autowired
        private InstanceLink instanceLink;

        public PedidoResumoModelAssembler() {
                super(PedidoController.class, PedidoResumoModel.class);
        }

        public PedidoResumoModel toModel(Pedido pedido) {
                PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getCodigo(), pedido);
                modelMapper.map(pedido, pedidoResumoModel);

                // RESTAURANTE
                pedidoResumoModel.getRestaurante()
                                .add(instanceLink.linkToRestaurantes(pedidoResumoModel.getRestaurante().getId()));
                pedidoResumoModel.getRestaurante()
                                .add(instanceLink.linkToRestaurantes(IanaLinkRelations.COLLECTION_VALUE));

                // CLIENTE
                pedidoResumoModel.getCliente().add(instanceLink.linkToUsuarios(pedidoResumoModel.getCliente().getId()));
                pedidoResumoModel.getCliente().add(instanceLink.linkToUsuarios(IanaLinkRelations.COLLECTION_VALUE));

                return pedidoResumoModel;
        }

        @Override
        public CollectionModel<PedidoResumoModel> toCollectionModel(Iterable<? extends Pedido> entities) {
                return super.toCollectionModel(entities)
                                .add(WebMvcLinkBuilder.linkTo(PedidoController.class).withSelfRel());
        }
}
