package com.algafood.algafoodapi.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProdutoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private boolean ativo;
}
