package com.algafood.algafoodapi.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algafood.algafoodapi.api.v1.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
public class CozinhaModelV2 extends RepresentationModel<CozinhaModelV2> {

    @JsonView(RestauranteView.Resumo.class)
    private Long idCozinha;

    @JsonView(RestauranteView.Resumo.class)
    private String nomeCozinha;

}
