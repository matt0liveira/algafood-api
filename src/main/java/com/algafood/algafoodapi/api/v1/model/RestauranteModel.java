package com.algafood.algafoodapi.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

// import com.algafood.algafoodapi.api.model.view.RestauranteView;
// import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteModel extends RepresentationModel<RestauranteModel> {

    // @JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
    private Long id;

    // @JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
    private String nome;

    // @JsonView(RestauranteView.Resumo.class)
    private BigDecimal frete;

    // @JsonView(RestauranteView.Resumo.class)
    private CozinhaModel cozinha;
    private Boolean ativo;
    private Boolean aberto;
    private EnderecoModel endereco;
}
