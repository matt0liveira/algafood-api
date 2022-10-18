package com.algafood.algafoodapi.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoFilter {

    @ApiModelProperty(value = "ID do cliente")
    private Long clienteId;

    @ApiModelProperty(value = "ID do restaurante")
    private Long restauranteId;

    @ApiModelProperty(value = "Data de início da criação do produto")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private OffsetDateTime dataCriacaoInicio;

    @ApiModelProperty(value = "Data de fim da criação do produto")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private OffsetDateTime dataCriacaoFim;
}
