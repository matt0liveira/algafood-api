package com.algafood.algafoodapi.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algafood.algafoodapi.domain.models.Restaurante;

public interface RestauranteRepositoryQueries {
    List<Restaurante> findByNomeAndFrete(String nome, BigDecimal freteInicial, BigDecimal freteFinal);
}
