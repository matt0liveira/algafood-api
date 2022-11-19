package com.algafood.algafoodapi.api.v1.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.ResourceUriHelper;
import com.algafood.algafoodapi.api.v1.assembler.UsuarioAssembler.UsuarioInputDisassembler;
import com.algafood.algafoodapi.api.v1.assembler.UsuarioAssembler.UsuarioModelAssembler;
import com.algafood.algafoodapi.api.v1.model.UsuarioModel;
import com.algafood.algafoodapi.api.v1.model.input.SenhaInput;
import com.algafood.algafoodapi.api.v1.model.input.UsuarioComSenhaInput;
import com.algafood.algafoodapi.api.v1.model.input.UsuarioInputModel;
import com.algafood.algafoodapi.api.v1.openapi.controller.UsuarioControllerOpenApi;
import com.algafood.algafoodapi.core.security.CheckSecurity;
import com.algafood.algafoodapi.domain.models.Usuario;
import com.algafood.algafoodapi.domain.repository.UsuarioRepository;
import com.algafood.algafoodapi.domain.service.CadastroUsuarioService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
@RestController
@RequestMapping(path = "v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioModelAssembler usuarioModel;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsutar
    @GetMapping
    public CollectionModel<UsuarioModel> listar() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarioModel.toCollectionModel(usuarios);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultarUsuario
    @GetMapping("/{usuarioId}")
    public UsuarioModel buscar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuario.findOrFail(usuarioId);

        return usuarioModel.toModel(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UsuarioModel> add(@RequestBody @Valid UsuarioComSenhaInput usuarioInputDTO) {
        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInputDTO);
        usuario = cadastroUsuario.salvar(usuario);

        return ResponseEntity
                .created(ResourceUriHelper.addUriInResponseHeader(usuario.getId()))
                .body(usuarioModel.toModel(usuario));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioModel> alterar(@RequestBody @Valid UsuarioInputModel usuarioInputDTO,
            @PathVariable Long usuarioId) {
        Usuario usuarioCurrent = cadastroUsuario.findOrFail(usuarioId);
        usuarioInputDisassembler.copyToDomainObject(usuarioInputDTO, usuarioCurrent);
        cadastroUsuario.salvar(usuarioCurrent);

        return ResponseEntity.status(HttpStatus.OK).body(usuarioModel.toModel(usuarioCurrent));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
    @PutMapping("/{usuarioId}/alterar-senha")
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
        cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getSenhaNova());
    }

}
