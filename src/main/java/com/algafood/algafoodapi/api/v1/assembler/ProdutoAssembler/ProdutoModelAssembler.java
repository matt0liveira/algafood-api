package com.algafood.algafoodapi.api.v1.assembler.ProdutoAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import com.algafood.algafoodapi.api.v1.InstanceLink;
import com.algafood.algafoodapi.api.v1.controllers.RestauranteProdutoController;
import com.algafood.algafoodapi.api.v1.model.ProdutoModel;
import com.algafood.algafoodapi.core.security.SecurityUtils;
import com.algafood.algafoodapi.domain.models.Produto;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstanceLink instanceLink;

    @Autowired
    private SecurityUtils securityUtils;

    public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }

    public ProdutoModel toModel(Produto produto) {
        ProdutoModel produtoModel = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());
        modelMapper.map(produto, produtoModel);

        try {
            if (securityUtils.podeConsultarRestaurantes()) {
                produtoModel.add(instanceLink.linkToProdutos(produto.getRestaurante().getId(),
                        IanaLinkRelations.COLLECTION_VALUE));

                produtoModel.add(
                        instanceLink.linkToFotoProduto(produto.getRestaurante().getId(), produtoModel.getId(),
                                "foto"));
            }
        } catch (HttpMediaTypeNotAcceptableException e) {
            e.printStackTrace();
        }

        return produtoModel;
    }

    @Override
    public CollectionModel<ProdutoModel> toCollectionModel(Iterable<? extends Produto> entities) {
        return super.toCollectionModel(entities);
    }
}
