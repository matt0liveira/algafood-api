package com.algafood.algafoodapi.domain.service;

import com.algafood.algafoodapi.domain.exceptions.EntityInUseException;
import com.algafood.algafoodapi.domain.exceptions.EstadoNotfoundException;
import com.algafood.algafoodapi.domain.models.Estado;
import com.algafood.algafoodapi.domain.repository.EstadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {
    
    @Autowired
    private EstadoRepository estadoRepository;

    private static final String ENTITY_IN_USE_MSG = "Estado de código %d não pode ser removido, pois está em uso.";

    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public void remover(Long estadoId) {
        try {
            estadoRepository.deleteById(estadoId);
            estadoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(ENTITY_IN_USE_MSG, estadoId));
        } catch (EmptyResultDataAccessException e) {
            throw new EstadoNotfoundException(estadoId);
        }
    }

    public Estado findOrFail(Long estadoId) {
        return estadoRepository.findById(estadoId)
            .orElseThrow(() -> new EstadoNotfoundException(estadoId));
    }

}
