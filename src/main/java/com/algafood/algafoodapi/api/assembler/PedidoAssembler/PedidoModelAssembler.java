package com.algafood.algafoodapi.api.assembler.PedidoAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.controllers.CidadeController;
import com.algafood.algafoodapi.api.controllers.FormaPagamentoControlller;
import com.algafood.algafoodapi.api.controllers.PedidoController;
import com.algafood.algafoodapi.api.controllers.RestauranteController;
import com.algafood.algafoodapi.api.controllers.UsuarioController;
import com.algafood.algafoodapi.api.model.PedidoModel;
import com.algafood.algafoodapi.domain.models.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        // CLIENTE
        pedidoModel.getCliente().add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder
                                .methodOn(UsuarioController.class)
                                .buscar(pedido.getCliente().getId()))
                        .withSelfRel());

        pedidoModel.getCliente().add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder
                                .methodOn(UsuarioController.class)
                                .listar())
                        .withRel(IanaLinkRelations.COLLECTION));

        // RESTAURANTE
        pedidoModel.getRestaurante().add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder
                                .methodOn(RestauranteController.class)
                                .buscar(pedidoModel.getRestaurante().getId()))
                        .withSelfRel());

        pedidoModel.getRestaurante().add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder
                                .methodOn(RestauranteController.class)
                                .listar(""))
                        .withRel(IanaLinkRelations.COLLECTION));

        // FORMA DE PAGAMENTO
        pedidoModel.getFormaPagamento().add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder
                                .methodOn(FormaPagamentoControlller.class)
                                .buscar(pedidoModel.getFormaPagamento().getId()))
                        .withSelfRel());

        pedidoModel.getFormaPagamento().add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder
                                .methodOn(FormaPagamentoControlller.class)
                                .listar(null))
                        .withRel(IanaLinkRelations.COLLECTION));

        // CIDADE
        pedidoModel.getEndereco().getCidade().add(
                WebMvcLinkBuilder
                        .linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(CidadeController.class)
                                        .buscar(pedidoModel.getEndereco().getCidade().getId()))
                        .withSelfRel());

        pedidoModel.getEndereco().getCidade().add(
                WebMvcLinkBuilder
                        .linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(CidadeController.class)
                                        .listar())
                        .withRel(IanaLinkRelations.COLLECTION));

        return pedidoModel;
    }

    @Override
    public CollectionModel<PedidoModel> toCollectionModel(Iterable<? extends Pedido> entities) {
        return super.toCollectionModel(entities).add(WebMvcLinkBuilder.linkTo(PedidoController.class).withSelfRel());
    }
}
