package com.algafood.algafoodapi.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algafood.algafoodapi.domain.exceptions.NegocioException;
import com.algafood.algafoodapi.domain.exceptions.UsuarioNotfoundException;
import com.algafood.algafoodapi.domain.models.Usuario;
import com.algafood.algafoodapi.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String senhaNova) {
        Usuario usuario = findOrFail(usuarioId);

        if(usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual incorreta.");
        }

        usuario.setSenha(senhaNova);
    }

    public Usuario findOrFail(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new UsuarioNotfoundException(usuarioId));
    }
    
}
