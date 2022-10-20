package com.algafood.algafoodapi.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.algafood.algafoodapi.api.assembler.UsuarioAssembler.UsuarioInputDisassembler;
import com.algafood.algafoodapi.api.assembler.UsuarioAssembler.UsuarioModelAssembler;
import com.algafood.algafoodapi.api.model.UsuarioDTO;
import com.algafood.algafoodapi.api.model.input.SenhaInput;
import com.algafood.algafoodapi.api.model.input.UsuarioComSenhaInput;
import com.algafood.algafoodapi.api.model.input.UsuarioInputDTO;
import com.algafood.algafoodapi.api.openapi.controller.UsuarioControllerOpenApi;
import com.algafood.algafoodapi.domain.models.Usuario;
import com.algafood.algafoodapi.domain.repository.UsuarioRepository;
import com.algafood.algafoodapi.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioModelAssembler usuarioModel;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @GetMapping
    public List<UsuarioDTO> listar() {
        return usuarioModel.toCollectionDTO(usuarioRepository.findAll());
    }

    @GetMapping("/{usuarioId}")
    public UsuarioDTO buscar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuario.findOrFail(usuarioId);

        return usuarioModel.toDTO(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO add(@RequestBody @Valid UsuarioComSenhaInput usuarioInputDTO) {
        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInputDTO);
        usuario = cadastroUsuario.salvar(usuario);

        return usuarioModel.toDTO(usuario);
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioDTO> alterar(@RequestBody @Valid UsuarioInputDTO usuarioInputDTO,
            @PathVariable Long usuarioId) {
        Usuario usuarioCurrent = cadastroUsuario.findOrFail(usuarioId);
        usuarioInputDisassembler.copyToDomainObject(usuarioInputDTO, usuarioCurrent);
        cadastroUsuario.salvar(usuarioCurrent);

        return ResponseEntity.status(HttpStatus.OK).body(usuarioModel.toDTO(usuarioCurrent));
    }

    @PutMapping("/{usuarioId}/alterar-senha")
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
        cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getSenhaNova());
    }

}
