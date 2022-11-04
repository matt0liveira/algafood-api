package com.algafood.algafoodapi.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.domain.repository.PedidoRepository;
import com.algafood.algafoodapi.domain.repository.RestauranteRepository;

@Component
public class SecurityUtils {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserIdAuthenticated() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();

        return jwt.getClaim("user_id");
    }

    public boolean gerenciaRestaurante(Long restauranteId) {
        if (restauranteId == null) {
            return false;
        }

        return restauranteRepository.existsResponsavel(restauranteId, getUserIdAuthenticated());
    }

    public boolean gerenciaPedidos(String codigoPedido) {
        if (codigoPedido == null) {
            return false;
        }

        return pedidoRepository.isPedidoGerenciadoPor(codigoPedido, getUserIdAuthenticated());
    }

    public boolean userAuthenticatedEquals(Long usuarioId) {
        return getUserIdAuthenticated() != null && usuarioId != null && getUserIdAuthenticated().equals(usuarioId);
    }

    public boolean hasAuthority(String authorityName) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(authorityName));
    }

    public boolean isAuthenticated() {
        return getAuthentication().isAuthenticated();
    }

    public boolean hasScopeWrite() {
        return hasAuthority("SCOPE_WRITE");
    }

    public boolean hasScopeRead() {
        return hasAuthority("SCOPE_READ");
    }

    public boolean podePesquisarPedidos() {
        return isAuthenticated() && hasScopeRead();
    }

    public boolean podePesquisarPedidos(Long clienteId, Long restauranteId) {
        return hasScopeRead() && (hasAuthority("CONSULTAR_PEDIDOS")) || userAuthenticatedEquals(clienteId)
                || gerenciaRestaurante(restauranteId);
    }

    public boolean podeConsultarRestaurantes() {
        return isAuthenticated() && hasScopeRead();
    }

    public boolean podeGerenciarCadastroRestaurantes() {
        return hasScopeWrite() && hasAuthority("EDITAR_RESTAURANTES");
    }

    public boolean podeGerenciarFuncionamentoRestaurantes(Long restauranteId) {
        return hasScopeWrite() && (hasAuthority("EDITAR_RESTAURANTES")) || gerenciaRestaurante(restauranteId);
    }

    public boolean podeConsultarFormasPagamento() {
        return isAuthenticated() && hasScopeRead();
    }

    public boolean podeConsultarCidades() {
        return isAuthenticated() && hasScopeRead();
    }

    public boolean podeConsultarEstados() {
        return isAuthenticated() && hasScopeRead();
    }

    public boolean podeConsultarCozinhas() {
        return isAuthenticated() && hasScopeRead();
    }

    public boolean podeConsultarEstatisticas() {
        return hasScopeRead() && hasAuthority("GERAR_RELATORIOS");
    }

    public boolean podeGerenciarPedidos(String codigoPedido) {
        return hasAuthority("SCOPE_WRITE") && hasAuthority("GERENCIAR_PEDIDOS") || gerenciaPedidos(codigoPedido);
    }

    public boolean podeConsultarUsuariosGruposPermissoes() {
        return hasAuthority("SCOPE_READ") && hasAuthority("CONSULTAR_USUARIOS_GRUPOS_PERMISSOES");
    }

    public boolean podeEditarUsuariosGruposPermissoes() {
        return hasAuthority("SCOPE_WRITE") && hasAuthority("EDITAR_USUARIOS_GRUPOS_PERMISSOES");
    }
}
