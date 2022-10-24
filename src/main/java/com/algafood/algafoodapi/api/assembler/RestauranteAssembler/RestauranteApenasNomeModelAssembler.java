package com.algafood.algafoodapi.api.assembler.RestauranteAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.InstanceLink;
import com.algafood.algafoodapi.api.controllers.RestauranteController;
import com.algafood.algafoodapi.api.model.RestauranteApenasNomeModel;
import com.algafood.algafoodapi.domain.models.Restaurante;

@Component
public class RestauranteApenasNomeModelAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstanceLink instanceLink;

    public RestauranteApenasNomeModelAssembler() {
        super(RestauranteController.class, RestauranteApenasNomeModel.class);
    }

    @Override
    public RestauranteApenasNomeModel toModel(Restaurante restaurante) {
        RestauranteApenasNomeModel restauranteResumoModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteResumoModel);

        restauranteResumoModel.add(instanceLink.linkToRestaurantes(IanaLinkRelations.COLLECTION_VALUE));

        return restauranteResumoModel;
    }

    @Override
    public CollectionModel<RestauranteApenasNomeModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(instanceLink.linkToRestaurantes(IanaLinkRelations.COLLECTION_VALUE));
    }

}
