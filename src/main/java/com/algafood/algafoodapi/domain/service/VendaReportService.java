package com.algafood.algafoodapi.domain.service;

import com.algafood.algafoodapi.domain.filter.VendaDiariaFilter;

public interface VendaReportService {
    byte[] emitirVendasDiarias(VendaDiariaFilter filter, String timeOffset);
}
