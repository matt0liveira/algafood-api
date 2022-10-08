package com.algafood.algafoodapi.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
    
    void armazenar(NewFoto newFoto);
    void remover(String nomeArquivo);

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
        private InputStream inputStream;
    }

}
