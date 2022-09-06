package com.algafood.algafoodapi.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteDTO {
    private Long id;
    private String nome;
    private BigDecimal frete;
    private CozinhaDTO cozinha;
    private Boolean ativo;
    private Boolean aberto;
    private EnderecoDTO endereco;
}
