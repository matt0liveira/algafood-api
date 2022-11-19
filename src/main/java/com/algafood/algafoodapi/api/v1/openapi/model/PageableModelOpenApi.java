package com.algafood.algafoodapi.api.v1.openapi.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "Pageable")
@Setter
@Getter
public class PageableModelOpenApi {

    @Schema(example = "0", description = "Número da página (começa em 0)")
    private int page;

    @Schema(example = "10", description = "Quantidade de elementos por página")
    private int size;

    @Schema(example = "nome,asc", description = "Nome da propriedade para ordenação")
    private List<String> sort;
}
