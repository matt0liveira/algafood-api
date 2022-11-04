package com.algafood.algafoodapi.api.v1.assembler.GrupoAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.v1.InstanceLink;
import com.algafood.algafoodapi.api.v1.controllers.GrupoController;
import com.algafood.algafoodapi.api.v1.model.GrupoModel;
import com.algafood.algafoodapi.core.security.SecurityUtils;
import com.algafood.algafoodapi.domain.models.Grupo;

@Component
public class GrupoModelAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstanceLink instanceLink;

    @Autowired
    private SecurityUtils securityUtils;

    public GrupoModelAssembler() {
        super(GrupoController.class, GrupoModel.class);
    }

    public GrupoModel toModel(Grupo grupo) {
        GrupoModel grupoModel = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoModel);

        if (securityUtils.podeConsultarUsuariosGruposPermissoes()) {
            grupoModel.add(instanceLink.linkToGrupos(IanaLinkRelations.COLLECTION_VALUE));

            grupoModel.add(instanceLink.linkToPermissoesGrupo(grupoModel.getId(), "permissoes"));
        }

        return grupoModel;
    }

    @Override
    public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
        CollectionModel<GrupoModel> collectionModel = super.toCollectionModel(entities);

        if (securityUtils.podeConsultarUsuariosGruposPermissoes()) {
            collectionModel.add(instanceLink.linkToGrupos());
        }

        return collectionModel;
    }
}
