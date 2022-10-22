package com.algafood.algafoodapi.api.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteResumoModel extends RepresentationModel<RestauranteResumoModel> {

    private Long id;
    private String nome;

}
