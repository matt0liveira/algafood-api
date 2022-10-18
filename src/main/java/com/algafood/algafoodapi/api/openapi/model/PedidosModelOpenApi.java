package com.algafood.algafoodapi.api.openapi.model;

import com.algafood.algafoodapi.api.model.PedidoResumoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PedidosModel")
@Setter
@Getter
public class PedidosModelOpenApi extends PagedModelOpenApi<PedidoResumoDTO> {

}
