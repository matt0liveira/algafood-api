package com.algafood.algafoodapi.api.v2.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(name = "PageModel")
public class PageModelOpenApiV2 {

    private Long size;
    private Long totalElements;
    private Long totalPages;
    private Long number;
}
