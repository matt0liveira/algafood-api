package com.algafood.algafoodapi.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "usuarios")
@Setter
@Getter
public class UsuarioDTO extends RepresentationModel<UsuarioDTO> {

    private Long id;
    private String nome;
    private String email;

}
