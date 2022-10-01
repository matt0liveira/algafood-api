package com.algafood.algafoodapi.infrastructure.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.algafood.algafoodapi.domain.filter.VendaDiariaFilter;
import com.algafood.algafoodapi.domain.models.Pedido;
import com.algafood.algafoodapi.domain.models.dto.VendaDiaria;
import com.algafood.algafoodapi.domain.models.enums.StatusPedido;
import com.algafood.algafoodapi.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultVendasDiarias(VendaDiariaFilter filter, String timeOffsset) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);   
        var root = query.from(Pedido.class);
        var predicates = new ArrayList<Predicate>();

        var functionCovertTzDataCriacao = builder.function(
            "convert_tz",
        Date.class,
            root.get("dataCriacao"),
            builder.literal("+00:00"),
            builder.literal(timeOffsset)
        );

        var functionDateDataCriacao = builder.function(
            "date", Date.class, functionCovertTzDataCriacao);

        var selection = builder.construct(VendaDiaria.class,
            functionDateDataCriacao ,
            builder.count(root.get("id")),
            builder.sum(root.get("valorTotal"))
        );

        if(filter.getRestauranteId() != null) {
            predicates.add(builder.equal(root.get("restaurante"), filter.getRestauranteId()));
        }

        if(filter.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoInicio()));
        }

        if(filter.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoFim()));
        }

        predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateDataCriacao);
        
        return manager.createQuery(query).getResultList();
    }
    
}
