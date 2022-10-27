package com.algafood.algafoodapi.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algafood.algafoodapi.api.v1.model.RestauranteModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("RestaurantesModel")
@Data
public class RestaurantesModelOpenApi {

    private RestauranteEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("RestauranteModel")
    @Setter
    @Getter
    public class RestauranteEmbeddedModelOpenApi {
        private List<RestauranteModel> restaurantes;
    }
}
