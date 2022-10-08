package com.algafood.algafoodapi.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algafood.algafoodapi.domain.models.FotoProduto;
import com.algafood.algafoodapi.domain.repository.ProdutoRepository;
import com.algafood.algafoodapi.domain.service.FotoStorageService.NewFoto;

@Service
public class CatalogoFotoProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorage;

    public FotoProduto salvar(FotoProduto foto, InputStream dataFoto) {
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();
        String newNomeArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
        String nomeArquivoCurrent = null;

        Optional<FotoProduto> fotoCurrent = produtoRepository.findFotoById(restauranteId, produtoId);

        if(fotoCurrent.isPresent()) {
            produtoRepository.delete(fotoCurrent.get());
            nomeArquivoCurrent = fotoCurrent.get().getNomeArquivo();
        }

        foto.setNomeArquivo(newNomeArquivo);
        foto = produtoRepository.save(foto);
        produtoRepository.flush();

        NewFoto newFoto = NewFoto.builder()
            .nomeArquivo(foto.getNomeArquivo())
            .inputStream(dataFoto)
            .build();

        fotoStorage.atualizar(nomeArquivoCurrent, newFoto);
        return foto;
    }

}
