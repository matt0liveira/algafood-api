package com.algafood.algafoodapi.api.v1.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FotoProdutoModel extends RepresentationModel<FotoProdutoModel> {

    private String nome;
    private String descricao;
    private String contentType;
    private Long tamanho;

}
