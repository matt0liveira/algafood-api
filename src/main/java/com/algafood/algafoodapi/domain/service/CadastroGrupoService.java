package com.algafood.algafoodapi.domain.service;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafood.algafoodapi.domain.exceptions.EntityInUseException;
import com.algafood.algafoodapi.domain.exceptions.GrupoNotfoundExcepetion;
import com.algafood.algafoodapi.domain.models.Grupo;
import com.algafood.algafoodapi.domain.models.Permissao;
import com.algafood.algafoodapi.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {
    
    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroPermissaoService cadastroPermissao;

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void remover(Long grupoId) {
        try {
            grupoRepository.deleteById(grupoId);
            grupoRepository.flush();
        } catch(EmptyResultDataAccessException e) {
            throw new GrupoNotfoundExcepetion(grupoId);
        } catch(DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("Grupo de código %d em uso.", grupoId));
        }
    }

    @Transactional
    public void associarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = findOrFail(grupoId);
        Permissao permissao = cadastroPermissao.findOrFail(permissaoId);

        grupo.associarPermissao(permissao);
    }

    @Transactional
    public void desassociarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = findOrFail(grupoId);
        Permissao permissao = cadastroPermissao.findOrFail(permissaoId);

        grupo.desassociarPermissao(permissao);
    }

    public Grupo findOrFail(Long grupoId) {
        return grupoRepository.findById(grupoId)
            .orElseThrow(() -> new GrupoNotfoundExcepetion(grupoId));
    }

}
