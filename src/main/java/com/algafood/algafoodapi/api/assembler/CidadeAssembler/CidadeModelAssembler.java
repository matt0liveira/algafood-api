package com.algafood.algafoodapi.api.assembler.CidadeAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.InstanceLink;
import com.algafood.algafoodapi.api.controllers.CidadeController;
import com.algafood.algafoodapi.api.model.CidadeModel;
import com.algafood.algafoodapi.domain.models.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

        public CidadeModelAssembler() {
                super(CidadeController.class, CidadeModel.class);
        }

        @Autowired
        private ModelMapper modelMapper;

        @Autowired
        private InstanceLink instanceLink;

        @Override
        public CidadeModel toModel(Cidade cidade) {
                CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);

                modelMapper.map(cidade, cidadeModel);

                cidadeModel.add(instanceLink.linkToCidades(cidadeModel.getId()));
                cidadeModel.add(instanceLink.linkToCidades(IanaLinkRelations.COLLECTION_VALUE));

                // ESTADO
                cidadeModel.getEstado().add(instanceLink.linkToEstados(cidadeModel.getEstado().getId()));

                return cidadeModel;
        }

        @Override
        public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
                return super.toCollectionModel(entities)
                                .add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
        }
}
