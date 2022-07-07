package com.algafood.algafoodapi.domain.service;

import com.algafood.algafoodapi.domain.exceptions.CidadeNotfoundException;
import com.algafood.algafoodapi.domain.exceptions.EntityInUseException;
import com.algafood.algafoodapi.domain.models.Cidade;
import com.algafood.algafoodapi.domain.models.Estado;
import com.algafood.algafoodapi.domain.repository.CidadeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCidadeService {
    
    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    private static final String ENTITY_IN_USE_MSG = "Cidade de código %d não pode ser removida, pois está em uso";

    @Transactional
    public void salvar(Cidade cidade) {

        Estado estado = cadastroEstado.findOrFail(cidade.getEstado().getId());
        cidade.setEstado(estado);
        cidadeRepository.save(cidade);

    }

    @Transactional
    public void remover(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNotfoundException(cidadeId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(ENTITY_IN_USE_MSG, cidadeId));
        }
    }

    public Cidade findOrFail(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
            .orElseThrow(() -> new CidadeNotfoundException(cidadeId));
    }

}
