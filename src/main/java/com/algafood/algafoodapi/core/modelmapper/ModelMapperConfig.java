package com.algafood.algafoodapi.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algafood.algafoodapi.api.model.EnderecoModel;
import com.algafood.algafoodapi.api.model.input.ItemPedidoInput;
import com.algafood.algafoodapi.domain.models.Endereco;
import com.algafood.algafoodapi.domain.models.ItemPedido;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    var modelMapper = new ModelMapper();

    modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
        .addMappings(mapper -> mapper.skip(ItemPedido::setId));

    modelMapper.getConfiguration().setAmbiguityIgnored(true);

    var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);

    enderecoToEnderecoModelTypeMap.<String>addMapping(
        enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
        (enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));

    return modelMapper;
  }

}
