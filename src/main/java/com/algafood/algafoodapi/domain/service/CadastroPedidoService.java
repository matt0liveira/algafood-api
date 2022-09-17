package com.algafood.algafoodapi.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algafood.algafoodapi.domain.exceptions.NegocioException;
import com.algafood.algafoodapi.domain.exceptions.PedidoNotfoundException;
import com.algafood.algafoodapi.domain.models.Cidade;
import com.algafood.algafoodapi.domain.models.FormaPagamento;
import com.algafood.algafoodapi.domain.models.Pedido;
import com.algafood.algafoodapi.domain.models.Produto;
import com.algafood.algafoodapi.domain.models.Restaurante;
import com.algafood.algafoodapi.domain.models.Usuario;
import com.algafood.algafoodapi.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Transactional
    public Pedido salvar(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        return pedidoRepository.save(pedido);

    }

    private void validarPedido(Pedido pedido) {
        Cidade cidade = cadastroCidade.findOrFail(pedido.getEndereco().getCidade().getId());
        Restaurante restaurante = cadastroRestaurante.findOrFail(pedido.getRestaurante().getId());
        Usuario cliente = cadastroUsuario.findOrFail(pedido.getCliente().getId());
        FormaPagamento formaPagamento = cadastroFormaPagamento.findOrFail(pedido.getFormaPagamento().getId());

        pedido.getEndereco().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if(restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento %s não é aceita neste restaurante!", formaPagamento.getDescricao()));
        }
    }

    private void validarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            Produto produto = cadastroProduto.findOrFail(pedido.getRestaurante().getId(), item.getProduto().getId());

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }

    public Pedido findOrFail(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new PedidoNotfoundException(pedidoId));
    }
}
