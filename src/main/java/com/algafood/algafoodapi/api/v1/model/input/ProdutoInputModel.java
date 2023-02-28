package com.algafood.algafoodapi.api.v1.model.input;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProdutoInputModel {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @PositiveOrZero
    private BigDecimal preco;

    @NotNull
    private Boolean ativo;
}
