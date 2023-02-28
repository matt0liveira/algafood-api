package com.algafood.algafoodapi.api.v1.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.algafood.algafoodapi.core.validation.FileContentType;
import com.algafood.algafoodapi.core.validation.FileSize;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FotoProdutoInputModel {

    @NotNull
    @FileSize(max = "500KB")
    @FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    private MultipartFile arquivo;

    @NotBlank
    private String descricao;

}
