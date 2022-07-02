package com.algafood.algafoodapi.domain.service;

import com.algafood.algafoodapi.domain.exceptions.EntityInUseException;
import com.algafood.algafoodapi.domain.exceptions.RestauranteNotfoundException;
import com.algafood.algafoodapi.domain.models.Cozinha;
import com.algafood.algafoodapi.domain.models.Restaurante;
import com.algafood.algafoodapi.domain.repository.RestauranteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {
    
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    private static final String ENTITY_IN_USE_MSG = "Restaurante de código %d não pode ser removido, pois está em uso!";

    public void salvar(Restaurante restaurante) {
        Cozinha cozinha = cadastroCozinha.findOrFail(restaurante.getCozinha().getId());
        restaurante.setCozinha(cozinha);
        restauranteRepository.save(restaurante);
    }

    public void excluir(Long restauranteId) {
        try {
            restauranteRepository.deleteById(restauranteId);
        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNotfoundException(restauranteId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(ENTITY_IN_USE_MSG, restauranteId));
        }
    }

    public Restaurante findOrFail(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
            .orElseThrow(() -> new RestauranteNotfoundException(restauranteId));
    }

}