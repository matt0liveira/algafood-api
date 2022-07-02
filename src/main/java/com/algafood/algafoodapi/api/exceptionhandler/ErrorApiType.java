package com.algafood.algafoodapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorApiType {
    
    RESOURCE_NOT_FOUND("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTITY_IN_USE("/entidade-em-uso", "Entidade em uso"),
    NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    UNREADABLE_BODY("/corpo-nao-legivel", "Corpo não legível"),
    PROPERTY_NON_EXISTENT("/propriedade-inexistente", "Propriedade inexistente"),
    METHOD_ARGUMENT_TYPE_MISMATCH("/tipo-do-parametro-imcompativel", "Tipo do parâmetro imcompatível"),
    SYSTEM_ERROR("/erro-interno", "Erro interno"),
    INVALID_DATA("/dados-invalidos", "Dados inválidos");

    private String title;
    private String uri;

    ErrorApiType(String path, String title) {
        this.uri = "https://siterqualquer.com.br" + path;
        this.title = title;
    }

}
