package com.algafood.algafoodapi.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.domain.repository.RestauranteRepository;

@Component
public class SecurityUtils {

    @Autowired
    private RestauranteRepository restauranteRepository;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserIdAuthenticated() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();

        return jwt.getClaim("user_id");
    }

    public boolean gerenciaRestaurante(Long restauranteId) {
        return restauranteRepository.existsResponsavel(restauranteId, getUserIdAuthenticated());
    }
}
