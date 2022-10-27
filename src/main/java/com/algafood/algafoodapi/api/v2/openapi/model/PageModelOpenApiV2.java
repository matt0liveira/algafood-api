package com.algafood.algafoodapi.api.v2.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("PageModel")
public class PageModelOpenApiV2 {

    private Long size;
    private Long totalElements;
    private Long totalPages;
    private Long number;
}
