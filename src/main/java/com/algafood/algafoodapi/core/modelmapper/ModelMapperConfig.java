package com.algafood.algafoodapi.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algafood.algafoodapi.api.model.EnderecoDTO;
import com.algafood.algafoodapi.domain.models.Endereco;

@Configuration
public class ModelMapperConfig {
    
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        
        var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);

        enderecoToEnderecoModelTypeMap.<String>addMapping(
          enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
          (enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));

        return modelMapper;
    }

}
