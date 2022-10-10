package com.algafood.algafoodapi.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algafood.algafoodapi.domain.models.Pedido;
import com.algafood.algafoodapi.domain.service.EnvioEmailService.Mensagem;

@Service
public class FluxoPedidoService {
    
    @Autowired
    private CadastroPedidoService cadastroPedido;

    @Autowired
    private EnvioEmailService envioEmail;

    @Transactional
    public void confirmar(String codigo) {
        Pedido pedido = cadastroPedido.findOrFail(codigo);

        pedido.confirmar();

        var mensagem = Mensagem.builder()
            .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
            .corpo("pedido-confirmado.html")
            .var("pedido", pedido)
            .destinatario(pedido.getCliente().getEmail())
            .build();

        envioEmail.enviar(mensagem);
    }

    @Transactional
    public void entregar(String codigo) {
        Pedido pedido = cadastroPedido.findOrFail(codigo);

       pedido.entregar();
    }

    @Transactional
    public void cancelar(String codigo) {
        Pedido pedido = cadastroPedido.findOrFail(codigo);

       pedido.cancelar();
    }

}
