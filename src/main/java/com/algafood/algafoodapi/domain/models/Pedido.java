package com.algafood.algafoodapi.domain.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.algafood.algafoodapi.domain.event.PedidoCanceladoEvent;
import com.algafood.algafoodapi.domain.event.PedidoConfirmadoEvent;
import com.algafood.algafoodapi.domain.exceptions.NegocioException;
import com.algafood.algafoodapi.domain.models.enums.StatusPedido;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class Pedido extends AbstractAggregateRoot<Pedido> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String codigo;

    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;

    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    @ManyToOne
    private Usuario cliente;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Restaurante restaurante;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private FormaPagamento formaPagamento;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    @Embedded
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;

    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;

    public void calcularValorTotal() {
        getItens().forEach(ItemPedido::calcularPrecoTotal);

        this.subtotal = getItens().stream()
                .map(item -> item.getPrecoTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    public void confirmar() {
        setStatus(StatusPedido.CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());

        registerEvent(new PedidoConfirmadoEvent(this));
    }

    public void entregar() {
        setStatus(StatusPedido.ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }

    public void cancelar() {
        setStatus(StatusPedido.CANCELADO);
        setDataCancelamento(OffsetDateTime.now());

        registerEvent(new PedidoCanceladoEvent(this));
    }

    public boolean canBeConfirmado() {
        return getStatus().canUpdateTo(StatusPedido.CONFIRMADO);
    }

    public boolean canBeEntregue() {
        return getStatus().canUpdateTo(StatusPedido.ENTREGUE);
    }

    public boolean canBeCancelado() {
        return getStatus().canUpdateTo(StatusPedido.CANCELADO);
    }

    private void setStatus(StatusPedido newStatus) {
        if (getStatus().cantUpdateTo(newStatus)) {
            throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s para %s",
                    getCodigo(), getStatus().getDescricao(), newStatus.getDescricao()));
        }

        this.status = newStatus;
    }

    @PrePersist
    private void randomCodigo() {
        setCodigo(UUID.randomUUID().toString());
    }

}
