package com.algafood.algafoodapi.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Setter
@Getter
public class RestauranteApenasNomeModel extends RepresentationModel<RestauranteApenasNomeModel> {
    private Long id;
    private String nome;
}
