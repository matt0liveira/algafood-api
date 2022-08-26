package com.algafood.algafoodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algafood.algafoodapi.domain.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
