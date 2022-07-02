package com.algafood.algafoodapi.domain.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.algafood.algafoodapi.domain.models.enums.StatusPedido;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private Long subtotal;

    @Column(nullable = false)
    private Long taxaFrete;

    @Column(nullable = false)
    private Long valorTotal;

    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    @ManyToOne
    private Usuario cliente;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Restaurante restaurante;

    @JoinColumn(nullable = false)
    @ManyToOne
    private FormaPagamento formaPagamento;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens = new ArrayList<>();

    @Embedded
    private Endereco endereco;

    private StatusPedido statusPedido;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    private LocalDateTime dataConfirmacao;
    private LocalDateTime dataEntrega;
    private LocalDateTime dataCancelamento;


}
