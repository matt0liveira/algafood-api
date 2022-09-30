package com.algafood.algafoodapi.domain.models.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class VendaDiaria {
 
    private Date data;
    private Long totalVendas;
    private BigDecimal totalFaturado;
}
