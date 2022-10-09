package com.algafood.algafoodapi.infrastructure.service.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algafood.algafoodapi.domain.service.FotoStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class StorageConfig {
    
    @Value("${algafood.storage.s3.id-chave-acesso}")
    private String idChaveAcesso;

    @Value("${algafood.storage.s3.chave-acesso-secreta}")
    private String chaveAcessoSecreta;

    @Value("${algafood.storage.s3.regiao}")
    private Regions regiao;

    @Value("${algafood.storage.type}")
    private StorageType type;

    public enum StorageType {
        LOCAL, S3
    }

    @Bean
    @ConditionalOnProperty(name = "algafood.storage.type", havingValue = "s3")
    public AmazonS3 amazonS3() {
        var credentials = new BasicAWSCredentials(idChaveAcesso, chaveAcessoSecreta);

        return AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(regiao)
        .build();
    }

    @Bean
    public FotoStorageService fotoStorageService() {
        if(StorageType.S3.equals(type)) {
            return new S3FotoStorageService();
        } else {
            return new LocalFotoStorageService();
        }
    }
}
