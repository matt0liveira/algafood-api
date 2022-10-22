package com.algafood.algafoodapi.api.assembler.PedidoAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.controllers.PedidoController;
import com.algafood.algafoodapi.api.controllers.RestauranteController;
import com.algafood.algafoodapi.api.controllers.UsuarioController;
import com.algafood.algafoodapi.api.model.PedidoResumoModel;
import com.algafood.algafoodapi.domain.models.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoResumoModel);

        // RESTAURANTE
        pedidoResumoModel.getRestaurante().add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder
                                .methodOn(RestauranteController.class)
                                .buscar(pedidoResumoModel.getRestaurante().getId()))
                        .withSelfRel());

        pedidoResumoModel.getRestaurante().add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder
                                .methodOn(RestauranteController.class)
                                .listar(""))
                        .withRel(IanaLinkRelations.COLLECTION));

        // CLIENTE
        pedidoResumoModel.getCliente().add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder
                                .methodOn(UsuarioController.class)
                                .buscar(pedidoResumoModel.getCliente().getId()))
                        .withSelfRel());

        pedidoResumoModel.getCliente().add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder
                                .methodOn(UsuarioController.class)
                                .listar())
                        .withRel(IanaLinkRelations.COLLECTION));

        return pedidoResumoModel;
    }

    @Override
    public CollectionModel<PedidoResumoModel> toCollectionModel(Iterable<? extends Pedido> entities) {
        return super.toCollectionModel(entities).add(WebMvcLinkBuilder.linkTo(PedidoController.class).withSelfRel());
    }
}
