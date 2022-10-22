package com.algafood.algafoodapi.api.assembler.UsuarioAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.controllers.UsuarioController;
import com.algafood.algafoodapi.api.controllers.UsuarioGrupoController;
import com.algafood.algafoodapi.api.model.UsuarioDTO;
import com.algafood.algafoodapi.domain.models.Usuario;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioModelAssembler() {
        super(UsuarioController.class, UsuarioDTO.class);
    }

    @Override
    public UsuarioDTO toModel(Usuario usuario) {
        UsuarioDTO usuarioModel = createModelWithId(usuario.getId(), usuario);

        modelMapper.map(usuario, usuarioModel);

        usuarioModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listar())
                .withRel(IanaLinkRelations.COLLECTION));

        usuarioModel.add(
                WebMvcLinkBuilder
                        .linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(UsuarioGrupoController.class)
                                        .listar(usuarioModel.getId()))
                        .withRel("grupos-usuarios"));

        return usuarioModel;
    }

    @Override
    public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities).add(WebMvcLinkBuilder.linkTo(UsuarioController.class).withSelfRel());
    }
}
