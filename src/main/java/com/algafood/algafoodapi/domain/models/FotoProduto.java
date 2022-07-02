package com.algafood.algafoodapi.domain.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class FotoProduto {
    
    @Column(name = "foto_nome")
    private String nome;

    @Column(name = "foto_descricao")
    private String descricao;

    @Column(name = "foto_contentType")
    private String contentType;

    @Column(name = "foto_tamanho")
    private Long tamanho;

}
