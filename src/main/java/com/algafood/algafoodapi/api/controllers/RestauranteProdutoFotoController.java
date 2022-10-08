package com.algafood.algafoodapi.api.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algafood.algafoodapi.api.assembler.FotoProdutoAssembler.FotoProdutoModelAssembler;
import com.algafood.algafoodapi.api.model.FotoProdutoDTO;
import com.algafood.algafoodapi.api.model.input.FotoProdutoInputDTO;
import com.algafood.algafoodapi.domain.models.FotoProduto;
import com.algafood.algafoodapi.domain.models.Produto;
import com.algafood.algafoodapi.domain.service.CadastroProdutoService;
import com.algafood.algafoodapi.domain.service.CatalogoFotoProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
    
    @Autowired
    CadastroProdutoService cadastroProduto;

    @Autowired
    CatalogoFotoProdutoService catalogoFoto;

    @Autowired
    private FotoProdutoModelAssembler fotoProdutoModel;

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

}
