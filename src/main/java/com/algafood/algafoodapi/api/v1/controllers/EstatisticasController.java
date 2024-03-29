package com.algafood.algafoodapi.api.v1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.v1.InstanceLink;
import com.algafood.algafoodapi.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import com.algafood.algafoodapi.core.security.CheckSecurity;
import com.algafood.algafoodapi.domain.filter.VendaDiariaFilter;
import com.algafood.algafoodapi.domain.models.dto.VendaDiaria;
import com.algafood.algafoodapi.domain.service.VendaReportService;
import com.algafood.algafoodapi.infrastructure.service.query.VendaQueryServiceImpl;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
@RestController
@RequestMapping(path = "v1/estatisticas", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstatisticasController implements EstatisticasControllerOpenApi {

    @Autowired
    private VendaQueryServiceImpl vendaQueryService;

    @Autowired
    private VendaReportService vendaReportService;

    @Autowired
    private InstanceLink instanceLink;

    @CheckSecurity.Estatisticas.PodeConsultar
    @GetMapping
    public EstatisticasModel root() {
        var estatisticasModel = new EstatisticasModel();

        estatisticasModel.add(instanceLink.linkToVendasDiarias("vendas-diarias"));

        return estatisticasModel;
    }

    @CheckSecurity.Estatisticas.PodeConsultar
    @GetMapping(path = "/vendas-diarias")
    public List<VendaDiaria> consultVendasDiarias(VendaDiariaFilter filter,
            @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return vendaQueryService.consultVendasDiarias(filter, timeOffset);
    }

    @CheckSecurity.Estatisticas.PodeConsultar
    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultVendasDiariasPdf(VendaDiariaFilter filter,
            @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {

        byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filter, timeOffset);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);

    }

    public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {
    }

}
