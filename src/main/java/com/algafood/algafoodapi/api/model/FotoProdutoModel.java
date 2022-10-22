package com.algafood.algafoodapi.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FotoProdutoModel {

    private String nome;
    private String descricao;
    private String contentType;
    private Long tamanho;

}
