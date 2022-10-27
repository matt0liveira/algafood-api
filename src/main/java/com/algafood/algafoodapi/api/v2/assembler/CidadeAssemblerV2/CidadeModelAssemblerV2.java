package com.algafood.algafoodapi.api.v2.assembler.CidadeAssemblerV2;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.v1.controllers.CidadeController;
import com.algafood.algafoodapi.api.v2.InstanceLinkV2;
import com.algafood.algafoodapi.api.v2.controllers.CidadeControllerV2;
import com.algafood.algafoodapi.api.v2.model.CidadeModelV2;
import com.algafood.algafoodapi.domain.models.Cidade;

@Component
public class CidadeModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeModelV2> {

        public CidadeModelAssemblerV2() {
                super(CidadeControllerV2.class, CidadeModelV2.class);
        }

        @Autowired
        private ModelMapper modelMapper;

        @Autowired
        private InstanceLinkV2 instanceLink;

        @Override
        public CidadeModelV2 toModel(Cidade cidade) {
                CidadeModelV2 cidadeModel = createModelWithId(cidade.getId(), cidade);

                modelMapper.map(cidade, cidadeModel);

                cidadeModel.add(instanceLink.linkToCidades(IanaLinkRelations.COLLECTION_VALUE));

                return cidadeModel;
        }

        @Override
        public CollectionModel<CidadeModelV2> toCollectionModel(Iterable<? extends Cidade> entities) {
                return super.toCollectionModel(entities)
                                .add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
        }
}
