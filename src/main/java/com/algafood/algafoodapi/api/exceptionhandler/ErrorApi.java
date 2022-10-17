package com.algafood.algafoodapi.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class ErrorApi {

    @ApiModelProperty(example = "400")
    private Integer status;

    @ApiModelProperty(example = "https://siterqualquer.com.br/corpo-nao-legivel")
    private String type;

    @ApiModelProperty(example = "Corpo não legível")
    private String title;

    @ApiModelProperty(example = "Corpo da requisição está inválido!")
    private String detail;

    @ApiModelProperty(example = "2022-10-17T19:01:17.6570745Z")
    private OffsetDateTime timestamp;
    private String userMessage;

    @ApiModelProperty(value = "Lista de objetos ou campos que causaram o erro", position = 10)
    private List<Object> objects;

    @Getter
    @Builder
    public static class Object {

        private String name;
        private String userMessage;

    }

}
