package com.algafood.algafoodapi.api.v1.assembler.RestauranteAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.v1.InstanceLink;
import com.algafood.algafoodapi.api.v1.controllers.RestauranteController;
import com.algafood.algafoodapi.api.v1.model.RestauranteApenasNomeModel;
import com.algafood.algafoodapi.core.security.SecurityUtils;
import com.algafood.algafoodapi.domain.models.Restaurante;

@Component
public class RestauranteApenasNomeModelAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstanceLink instanceLink;

    @Autowired
    private SecurityUtils securityUtils;

    public RestauranteApenasNomeModelAssembler() {
        super(RestauranteController.class, RestauranteApenasNomeModel.class);
    }

    @Override
    public RestauranteApenasNomeModel toModel(Restaurante restaurante) {
        RestauranteApenasNomeModel restauranteResumoModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteResumoModel);

        if (securityUtils.podeConsultarRestaurantes()) {
            restauranteResumoModel.add(instanceLink.linkToRestaurantes(IanaLinkRelations.COLLECTION_VALUE));
        }

        return restauranteResumoModel;
    }

    @Override
    public CollectionModel<RestauranteApenasNomeModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteApenasNomeModel> collectionModel = super.toCollectionModel(entities);

        if (securityUtils.podeConsultarRestaurantes()) {
            collectionModel.add(instanceLink.linkToRestaurantes(IanaLinkRelations.COLLECTION_VALUE));
        }

        return collectionModel;
    }

}
