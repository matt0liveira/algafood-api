package com.algafood.algafoodapi.domain.service;

import java.util.List;

import com.algafood.algafoodapi.domain.filter.VendaDiariaFilter;
import com.algafood.algafoodapi.domain.models.dto.VendaDiaria;

public interface VendaQueryService {
    
    List<VendaDiaria> consultVendasDiarias(VendaDiariaFilter filter);

}
