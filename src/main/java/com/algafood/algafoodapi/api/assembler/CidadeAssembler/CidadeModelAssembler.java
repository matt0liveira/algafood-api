package com.algafood.algafoodapi.api.assembler.CidadeAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.controllers.CidadeController;
import com.algafood.algafoodapi.api.controllers.EstadoController;
import com.algafood.algafoodapi.api.model.CidadeDTO;
import com.algafood.algafoodapi.domain.models.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {

    public CidadeModelAssembler() {
        super(CidadeController.class, CidadeDTO.class);
    }

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CidadeDTO toModel(Cidade cidade) {
        CidadeDTO cidadeModel = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModel);

        cidadeModel.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(CidadeController.class)
                        .buscar(cidadeModel.getId()))
                .withSelfRel());

        // HARD CODE -> NÃO RECOMENDADO
        // cidadeModel.add(Link.of("http://api.algafood.local:8080/cidades",
        // IanaLinkRelations.COLLECTION));

        // MONTANDO LINK DE FORMA DINÂMICA COM SLASH
        // cidadeModel.add(WebMvcLinkBuilder
        // .linkTo(CidadeController.class)
        // .withRel(IanaLinkRelations.COLLECTION));

        // MONTANDO LINK DE FORMA DINÂMICA APONTANDO PARA MÉTODO
        cidadeModel.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(CidadeController.class)
                        .listar())
                .withRel(IanaLinkRelations.COLLECTION));

        // HARD CODE -> NÃO RECOMENDADO
        // cidadeModel.getEstado().add(Link.of("http://api.algafood.local:8080/estados/1"));

        // MONTANDO LINK DE FORMA DINÂMICA COM SLASH
        // cidadeModel.getEstado().add(WebMvcLinkBuilder
        // .linkTo(EstadoController.class)
        // .slash(cidadeModel.getEstado().getId())
        // .withSelfRel());

        // MONTANDO LINK DE FORMA DINÂMICA APONTANDO PARA MÉTODO
        cidadeModel.getEstado().add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EstadoController.class)
                        .buscar(cidadeModel.getEstado().getId()))
                .withSelfRel());

        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities).add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
    }
}
