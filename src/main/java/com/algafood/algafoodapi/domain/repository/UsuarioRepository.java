package com.algafood.algafoodapi.domain.repository;

import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;

import com.algafood.algafoodapi.domain.models.Usuario;

public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
