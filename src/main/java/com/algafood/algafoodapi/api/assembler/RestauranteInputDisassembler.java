package com.algafood.algafoodapi.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.model.input.RestauranteInputDTO;
import com.algafood.algafoodapi.domain.models.Restaurante;

@Component
public class RestauranteInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInputDTO restauranteInputDTO) {
        return modelMapper.map(restauranteInputDTO, Restaurante.class);
    }

}