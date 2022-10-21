package com.algafood.algafoodapi.api.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeDTO extends RepresentationModel<CidadeDTO> {

    private Long id;
    private String nome;
    private EstadoDTO estado;

}
