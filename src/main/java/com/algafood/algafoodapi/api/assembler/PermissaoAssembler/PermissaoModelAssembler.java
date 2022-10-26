package com.algafood.algafoodapi.api.assembler.PermissaoAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.InstanceLink;
import com.algafood.algafoodapi.api.controllers.PermissaoController;
import com.algafood.algafoodapi.api.model.PermissaoModel;
import com.algafood.algafoodapi.domain.models.Permissao;

@Component
public class PermissaoModelAssembler extends RepresentationModelAssemblerSupport<Permissao, PermissaoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstanceLink instanceLink;

    public PermissaoModelAssembler() {
        super(PermissaoController.class, PermissaoModel.class);
    }

    public PermissaoModel toModel(Permissao permissao) {
        PermissaoModel permissaoModel = createModelWithId(permissao.getId(), permissao);
        modelMapper.map(permissao, permissaoModel);

        permissaoModel.add(instanceLink.linkToPermissoes(IanaLinkRelations.COLLECTION_VALUE));

        return permissaoModel;
    }

    @Override
    public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> entities) {
        return super.toCollectionModel(entities).add(instanceLink.linkToPermissoes());
    }
}
