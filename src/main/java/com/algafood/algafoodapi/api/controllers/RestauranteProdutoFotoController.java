package com.algafood.algafoodapi.api.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algafood.algafoodapi.api.assembler.FotoProdutoAssembler.FotoProdutoModelAssembler;
import com.algafood.algafoodapi.api.model.FotoProdutoDTO;
import com.algafood.algafoodapi.api.model.input.FotoProdutoInputDTO;
import com.algafood.algafoodapi.domain.exceptions.EntityNotfoundException;
import com.algafood.algafoodapi.domain.models.FotoProduto;
import com.algafood.algafoodapi.domain.models.Produto;
import com.algafood.algafoodapi.domain.service.CadastroProdutoService;
import com.algafood.algafoodapi.domain.service.CatalogoFotoProdutoService;
import com.algafood.algafoodapi.domain.service.FotoStorageService;
import com.algafood.algafoodapi.domain.service.FotoStorageService.FotoRecuperada;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
    
    @Autowired
    CadastroProdutoService cadastroProduto;

    @Autowired
    CatalogoFotoProdutoService catalogoFoto;

    @Autowired
    private FotoProdutoModelAssembler fotoProdutoModel;

    @Autowired
    private FotoStorageService fotoStorage;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInputDTO fotoProdutoInput) throws IOException {
        Produto produto = cadastroProduto.findOrFail(restauranteId, produtoId);

        MultipartFile file = fotoProdutoInput.getArquivo();
        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(file.getContentType());
        foto.setTamanho(file.getSize());
        foto.setNomeArquivo(file.getOriginalFilename());

        FotoProduto fotoSalva = catalogoFoto.salvar(foto, file.getInputStream());

        return fotoProdutoModel.toDTO(fotoSalva);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        return fotoProdutoModel.toDTO(catalogoFoto.findOrFail(restauranteId, produtoId));
    }

    @GetMapping
    public ResponseEntity<?> servirFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {
            FotoProduto foto = catalogoFoto.findOrFail(restauranteId, produtoId);
            
            MediaType mediaTypeFoto = MediaType.parseMediaType(foto.getContentType());
            List<MediaType> mediaTypeAccepts = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatiblidadeMediaType(mediaTypeFoto, mediaTypeAccepts);

            FotoRecuperada fotoRecuperada = fotoStorage.recuperar(foto.getNomeArquivo());

            if(fotoRecuperada.hasUrl()) {
                return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                    .build();
            } else {
                return ResponseEntity.ok()
                    .contentType(mediaTypeFoto)
                    .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }
    
        } catch(EntityNotfoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        catalogoFoto.excluir(restauranteId, produtoId);
    }

    private void verificarCompatiblidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypeAccepts) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypeAccepts.stream()
            .anyMatch(mediaTypeAccept -> mediaTypeAccept.isCompatibleWith(mediaTypeFoto));

            if(!compativel) {
                throw new HttpMediaTypeNotAcceptableException(mediaTypeAccepts);
            }
    }

}
