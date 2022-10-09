package com.algafood.algafoodapi.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
    
    void armazenar(NewFoto newFoto);
    void remover(String nomeArquivo);
    FotoRecuperada recuperar(String nomeArquivo);

    default String gerarNomeArquivo(String nomeArquivo) {
        return UUID.randomUUID() + "_" + nomeArquivo;
    }

    default void atualizar(String nomeArquivoOld, NewFoto newFoto) {
        this.armazenar(newFoto);

        if(nomeArquivoOld != null) {
            this.remover(nomeArquivoOld);
        }
    }

    @Getter
    @Builder
    class NewFoto {
        private String nomeArquivo;
        private String contentType;
        private InputStream inputStream;
    }

    @Builder
    @Getter
    public class FotoRecuperada {
        private InputStream inputStream;
        private String url;

        public boolean hasUrl() {
            return url != null;
        }

        public boolean hasInputStream() {
            return inputStream != null;
        }
    }

}
