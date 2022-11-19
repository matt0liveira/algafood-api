package com.algafood.algafoodapi.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@Setter
@Getter
public class CidadeModelV2 extends RepresentationModel<CidadeModelV2> {

    private Long idCidade;
    private String nomeCidade;
    private Long idEstado;
    private String nomeEstado;
}
