package com.algafood.algafoodapi.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.algafood.algafoodapi.domain.exceptions.NegocioException;
import com.algafood.algafoodapi.domain.exceptions.UsuarioNotfoundException;
import com.algafood.algafoodapi.domain.models.Grupo;
import com.algafood.algafoodapi.domain.models.Usuario;
import com.algafood.algafoodapi.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        usuarioRepository.detach(usuario);
        Optional<Usuario> usuarioExis = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioExis.isPresent() && !usuarioExis.get().equals(usuario)) {
            throw new NegocioException(
                    String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
        }

        if (usuario.isNew()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String senhaNova) {
        Usuario usuario = findOrFail(usuarioId);

        if (passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            usuario.setSenha(passwordEncoder.encode(senhaNova));
        } else {
            throw new NegocioException("Senha incorreta. Tente novamente");
        }

    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = findOrFail(usuarioId);
        Grupo grupo = cadastroGrupo.findOrFail(grupoId);

        usuario.associarGrupo(grupo);
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = findOrFail(usuarioId);
        Grupo grupo = cadastroGrupo.findOrFail(grupoId);

        usuario.desassociarGrupo(grupo);
    }

    public Usuario findOrFail(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNotfoundException(usuarioId));
    }

}
