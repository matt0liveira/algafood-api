package com.algafood.algafoodapi.api.v1.assembler.RestauranteAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.v1.InstanceLink;
import com.algafood.algafoodapi.api.v1.controllers.RestauranteController;
import com.algafood.algafoodapi.api.v1.model.RestauranteResumoModel;
import com.algafood.algafoodapi.core.security.SecurityUtils;
import com.algafood.algafoodapi.domain.models.Restaurante;

@Relation(collectionRelation = "restaurantes")
@Component
public class RestauranteResumoModelAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstanceLink instanceLink;

    @Autowired
    private SecurityUtils securityUtils;

    public RestauranteResumoModelAssembler() {
        super(RestauranteController.class, RestauranteResumoModel.class);
    }

    @Override
    public RestauranteResumoModel toModel(Restaurante restaurante) {
        RestauranteResumoModel restauranteResumoModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteResumoModel);

        if (securityUtils.podeConsultarCozinhas()) {
            restauranteResumoModel.getCozinha()
                    .add(instanceLink.linkToCozinha(restauranteResumoModel.getCozinha().getId()));
        }

        return restauranteResumoModel;
    }

    @Override
    public CollectionModel<RestauranteResumoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteResumoModel> collectionModel = super.toCollectionModel(entities);

        if (securityUtils.podeConsultarRestaurantes()) {
            collectionModel.add(instanceLink.linkToRestaurantes(IanaLinkRelations.COLLECTION_VALUE));
        }

        return collectionModel;
    }

}
