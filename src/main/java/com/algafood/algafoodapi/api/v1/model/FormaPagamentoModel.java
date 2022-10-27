package com.algafood.algafoodapi.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "formas-pagamentos")
@Setter
@Getter
public class FormaPagamentoModel extends RepresentationModel<FormaPagamentoModel> {

    private Long id;
    private String descricao;
}
