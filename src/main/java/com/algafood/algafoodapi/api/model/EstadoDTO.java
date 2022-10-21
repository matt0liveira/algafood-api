package com.algafood.algafoodapi.api.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoDTO extends RepresentationModel<EstadoDTO> {

    private Long id;
    private String nome;

}
