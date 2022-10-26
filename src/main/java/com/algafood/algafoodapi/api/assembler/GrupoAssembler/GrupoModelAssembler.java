package com.algafood.algafoodapi.api.assembler.GrupoAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.InstanceLink;
import com.algafood.algafoodapi.api.controllers.GrupoController;
import com.algafood.algafoodapi.api.model.GrupoModel;
import com.algafood.algafoodapi.domain.models.Grupo;

@Component
public class GrupoModelAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstanceLink instanceLink;

    public GrupoModelAssembler() {
        super(GrupoController.class, GrupoModel.class);
    }

    public GrupoModel toModel(Grupo grupo) {
        GrupoModel grupoModel = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoModel);

        grupoModel.add(instanceLink.linkToGrupos(IanaLinkRelations.COLLECTION_VALUE));

        grupoModel.add(instanceLink.linkToPermissoesGrupo(grupoModel.getId(), "permissoes"));

        return grupoModel;
    }

    @Override
    public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities)
                .add(instanceLink.linkToGrupos());
    }
}
