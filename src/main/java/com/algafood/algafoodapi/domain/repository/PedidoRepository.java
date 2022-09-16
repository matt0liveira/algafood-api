package com.algafood.algafoodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algafood.algafoodapi.domain.models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
}
