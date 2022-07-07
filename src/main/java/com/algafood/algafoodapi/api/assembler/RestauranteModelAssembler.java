package com.algafood.algafoodapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.model.CozinhaDTO;
import com.algafood.algafoodapi.api.model.RestauranteDTO;
import com.algafood.algafoodapi.domain.models.Restaurante;

@Component
public class RestauranteModelAssembler {

    public RestauranteDTO toDTO(Restaurante restaurante) {
        CozinhaDTO cozinhaDTO = new CozinhaDTO();
        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());

        RestauranteDTO restauranteDTO = new RestauranteDTO();
        restauranteDTO.setId(restaurante.getId());
        restauranteDTO.setNome(restaurante.getNome());
        restauranteDTO.setFrete(restaurante.getTaxaFrete());
        restauranteDTO.setCozinha(cozinhaDTO);
        return restauranteDTO;
    }

    public List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes) {
        return restaurantes.stream()
            .map(restaurante -> toDTO(restaurante))
            .collect(Collectors.toList());
    }
    
}
