package com.algafood.algafoodapi.api.v1.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeResumoModel extends RepresentationModel<CidadeResumoModel> {

    private Long id;
    private String nome;
    private String estado;
}
