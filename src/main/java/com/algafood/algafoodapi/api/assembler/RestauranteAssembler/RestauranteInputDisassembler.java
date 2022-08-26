package com.algafood.algafoodapi.api.assembler.RestauranteAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.model.input.RestauranteInputDTO;
import com.algafood.algafoodapi.domain.models.Cidade;
import com.algafood.algafoodapi.domain.models.Cozinha;
import com.algafood.algafoodapi.domain.models.Restaurante;

@Component
public class RestauranteInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInputDTO restauranteInputDTO) {
        return modelMapper.map(restauranteInputDTO, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteInputDTO restauranteInputDTO, Restaurante restaurante) {
        // Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());

        if(restaurante.getEndereco() != null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }
        modelMapper.map(restauranteInputDTO, restaurante);
    }

}