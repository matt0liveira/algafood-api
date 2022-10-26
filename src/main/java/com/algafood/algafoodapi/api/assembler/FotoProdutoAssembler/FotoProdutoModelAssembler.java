package com.algafood.algafoodapi.api.assembler.FotoProdutoAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import com.algafood.algafoodapi.api.InstanceLink;
import com.algafood.algafoodapi.api.controllers.RestauranteProdutoFotoController;
import com.algafood.algafoodapi.api.model.FotoProdutoModel;
import com.algafood.algafoodapi.domain.models.FotoProduto;

@Component
public class FotoProdutoModelAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstanceLink instanceLink;

    public FotoProdutoModelAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
    }

    public FotoProdutoModel toModel(FotoProduto foto) {
        FotoProdutoModel fotoProdutoModel = modelMapper.map(foto, FotoProdutoModel.class);

        try {
            fotoProdutoModel.add(instanceLink.linkToFotoProduto(foto.getRestauranteId(), foto.getProduto().getId(),
                    IanaLinkRelations.SELF_VALUE));

            fotoProdutoModel
                    .add(instanceLink.linkToProduto(foto.getRestauranteId(), foto.getProduto().getId(), "produto"));

        } catch (HttpMediaTypeNotAcceptableException e) {
            e.printStackTrace();
        }

        return fotoProdutoModel;
    }
}
