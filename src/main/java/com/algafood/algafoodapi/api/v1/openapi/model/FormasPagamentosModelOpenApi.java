package com.algafood.algafoodapi.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algafood.algafoodapi.api.v1.model.FormaPagamentoModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Schema(name = "FormasPagamentosModel")
public class FormasPagamentosModelOpenApi {

    private FormaPagamentoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Schema(name = "FormaPagamentoModel")
    @Setter
    @Getter
    public class FormaPagamentoEmbeddedModelOpenApi {
        private List<FormaPagamentoModel> formasPagamentos;
    }
}
