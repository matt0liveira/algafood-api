package com.algafood.algafoodapi.infrastructure.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.algafood.algafoodapi.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${algafood.storage.s3.bucket}")
    private String bucket;

    @Value("${algafood.storage.s3.diretorio-fotos}")
    private String diretorioFotos;

    @Override
    public void armazenar(NewFoto newFoto) {
        try {
            String pathFile = getPathFile(newFoto.getNomeArquivo());
            ObjectMetadata objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(newFoto.getContentType());
    
            var putObjectRequest = new PutObjectRequest(
                bucket,
                pathFile,
                newFoto.getInputStream(),
                objectMetaData
            ).withCannedAcl(CannedAccessControlList.PublicRead);
    
            amazonS3.putObject(putObjectRequest);
        } catch(Exception e) {
            throw new StorageException("Não foi possível enviar arquivo para Amazon S3", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            nomeArquivo = getPathFile(nomeArquivo);
            var deleteObjectRequest = new DeleteObjectRequest(bucket, nomeArquivo);
            amazonS3.deleteObject(deleteObjectRequest);
        } catch(Exception e) {
            throw new StorageException("Não foi possível excluir o arquivo.", e);
        }
        
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        String pathFile = getPathFile(nomeArquivo);

        URL url = amazonS3.getUrl(bucket, pathFile);
        return FotoRecuperada.builder()
            .url(url.toString())
            .build();
    }

    private String getPathFile(String nomeArquivo) {
        return String.format("%s/%s", diretorioFotos, nomeArquivo);
    }
    
}
