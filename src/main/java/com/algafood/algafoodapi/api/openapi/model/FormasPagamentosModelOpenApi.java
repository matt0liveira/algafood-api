package com.algafood.algafoodapi.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algafood.algafoodapi.api.model.FormaPagamentoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@ApiModel("FormasPagamentosModel")
public class FormasPagamentosModelOpenApi {

    private FormaPagamentoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("FormaPagamentoModel")
    @Setter
    @Getter
    public class FormaPagamentoEmbeddedModelOpenApi {
        private List<FormaPagamentoModel> formasPagamentos;
    }
}
