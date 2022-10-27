package com.algafood.algafoodapi.api.v1.assembler.RestauranteAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.v1.InstanceLink;
import com.algafood.algafoodapi.api.v1.controllers.RestauranteController;
import com.algafood.algafoodapi.api.v1.model.RestauranteModel;
import com.algafood.algafoodapi.domain.models.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstanceLink instanceLink;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(instanceLink.linkToRestaurantes(IanaLinkRelations.COLLECTION_VALUE));

        // COZINHA
        restauranteModel.getCozinha().add(instanceLink.linkToCozinha(restauranteModel.getCozinha().getId()));
        restauranteModel.getCozinha().add(instanceLink.linkToCozinhas());

        // CIDADE
        if (restauranteModel.getEndereco() != null && restauranteModel.getEndereco().getCidade() != null) {
            restauranteModel.getEndereco().getCidade()
                    .add(instanceLink.linkToCidade(restauranteModel.getEndereco().getCidade().getId()));

            restauranteModel.getEndereco().getCidade()
                    .add(instanceLink.linkToCidades(IanaLinkRelations.COLLECTION_VALUE));
        }

        // RESPONSÁVEIS
        restauranteModel.add(instanceLink.linkToRestaurantesUsuarios(restauranteModel.getId(), "responsaveis"));

        // FORMAS DE PAGAMENTO
        restauranteModel
                .add(instanceLink.linkToRestaurantesFormasPagamento(restauranteModel.getId(), "formas-pagamento"));

        // PRODUTOS
        restauranteModel.add(instanceLink.linkToProdutos(restauranteModel.getId(), "produtos"));

        // ATIVAÇÃO DE RESTAURANTE
        if (restaurante.podeAtivar()) {
            restauranteModel.add(instanceLink.linkToAtivacaoRestaurante(restauranteModel.getId(), "ativar"));
        }

        if (restaurante.podeInativar()) {
            restauranteModel.add(instanceLink.linkToInativacaoRestaurante(restauranteModel.getId(), "inativar"));
        }

        // ABERTURA DE RESTAURANTE
        if (restaurante.podeAbrir()) {
            restauranteModel.add(instanceLink.linkToAberturaRestaurante(restauranteModel.getId(), "abrir"));
        }

        if (restaurante.podeFechar()) {
            restauranteModel.add(instanceLink.linkToFechamentoestaurante(restauranteModel.getId(), "fechar"));
        }

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(instanceLink.linkToRestaurantes(IanaLinkRelations.COLLECTION_VALUE));
    }

}
