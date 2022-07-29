package com.algafood.algafoodapi.domain.service;

import com.algafood.algafoodapi.domain.exceptions.CozinhaNotfoundException;
import com.algafood.algafoodapi.domain.exceptions.EntityInUseException;
import com.algafood.algafoodapi.domain.models.Cozinha;
import com.algafood.algafoodapi.domain.repository.CozinhaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private static final String ENTITY_IN_USE_MSG = "Cozinha de código %d não pode ser removida, pois está em uso.";

    public Cozinha salvar(Cozinha cozinha) {

        return cozinhaRepository.save(cozinha);

    }

    public void remover(Long cozinhaId) {
        try {
            cozinhaRepository.deleteById(cozinhaId);
            cozinhaRepository.flush();
        } catch(EmptyResultDataAccessException e) {
            throw new CozinhaNotfoundException(cozinhaId);
        } catch(DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(ENTITY_IN_USE_MSG, cozinhaId));
        }
    }

    public Cozinha findOrFail(Long cozinhaId) {
        return cozinhaRepository.findById(cozinhaId)
            .orElseThrow(() -> new CozinhaNotfoundException(cozinhaId));
    }
}
