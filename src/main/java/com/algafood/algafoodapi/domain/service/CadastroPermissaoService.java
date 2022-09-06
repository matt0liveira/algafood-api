package com.algafood.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algafood.algafoodapi.domain.exceptions.PermissaoNotfoundException;
import com.algafood.algafoodapi.domain.models.Permissao;
import com.algafood.algafoodapi.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {
    
    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao findOrFail(Long permissaoId) {
        return permissaoRepository.findById(permissaoId)
            .orElseThrow(() -> new PermissaoNotfoundException(permissaoId));
    }
}
