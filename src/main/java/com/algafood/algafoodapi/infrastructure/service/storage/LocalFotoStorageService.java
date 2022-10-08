package com.algafood.algafoodapi.infrastructure.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.algafood.algafoodapi.domain.service.FotoStorageService;

@Service
public class LocalFotoStorageService implements FotoStorageService {
    
    @Value("${algafood.storage.local.directory-local}")
    private Path directoryLocal;

    @Override
    public void armazenar(NewFoto newFoto) {
        try {
            Path filePath = getFilePath(newFoto.getNomeArquivo());
            FileCopyUtils.copy(newFoto.getInputStream(), Files.newOutputStream(filePath));
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazernar o arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        Path filePath = getFilePath(nomeArquivo);
        
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new StorageException("Não foi possível excluir o arquivo.", e);
        }
    }

    private Path getFilePath(String fileName) {
        return directoryLocal.resolve(Path.of(fileName));
    }

}
