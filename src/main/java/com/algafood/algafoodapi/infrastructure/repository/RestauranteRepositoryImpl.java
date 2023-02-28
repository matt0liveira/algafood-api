package com.algafood.algafoodapi.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algafood.algafoodapi.domain.models.Restaurante;
import com.algafood.algafoodapi.domain.repository.RestauranteRepositoryQueries;


@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {
    
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> findByNomeAndFrete(String nome, BigDecimal freteInicial, BigDecimal freteFinal) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class);
        var predicates = new ArrayList<Predicate>();

        if(StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if(freteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), freteInicial));
        }

        if(freteFinal != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), freteFinal));
        }


        criteria.where(predicates.toArray(new Predicate[0]));

        return manager.createQuery(criteria).getResultList();
    }

}