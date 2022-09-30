package com.algafood.algafoodapi.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.domain.filter.VendaDiariaFilter;
import com.algafood.algafoodapi.domain.models.dto.VendaDiaria;
import com.algafood.algafoodapi.infrastructure.service.VendaQueryServiceImpl;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {
    
    @Autowired
    private VendaQueryServiceImpl vendaQueryService;

    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> consultVendasDiarias(VendaDiariaFilter filter) {
        return vendaQueryService.consultVendasDiarias(filter);
    }

}
