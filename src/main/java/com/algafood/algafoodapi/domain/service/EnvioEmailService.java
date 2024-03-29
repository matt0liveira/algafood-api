package com.algafood.algafoodapi.domain.service;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface EnvioEmailService {
    void enviar(Mensagem mensagem);

    @Getter
    @Builder
    public class Mensagem {

        @Singular
        private Set<String> destinatarios;

        @NonNull
        private String assunto;

        @NonNull
        private String corpo;

        @Singular
        private Map<String, Object> vars;
    }
}
