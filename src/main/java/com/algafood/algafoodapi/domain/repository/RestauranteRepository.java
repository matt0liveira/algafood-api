package com.algafood.algafoodapi.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algafood.algafoodapi.domain.models.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries {
    public List<Restaurante> consultarTaxa();

    public boolean existsResponsavel(Long restauranteId, Long usuarioId);
}
