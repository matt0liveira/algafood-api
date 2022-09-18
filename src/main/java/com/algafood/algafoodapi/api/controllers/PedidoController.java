package com.algafood.algafoodapi.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.assembler.PedidoAssembler.PedidoInputDisassembler;
import com.algafood.algafoodapi.api.assembler.PedidoAssembler.PedidoModelAssembler;
import com.algafood.algafoodapi.api.assembler.PedidoAssembler.PedidoResumoModelAssembler;
import com.algafood.algafoodapi.api.model.PedidoDTO;
import com.algafood.algafoodapi.api.model.PedidoResumoDTO;
import com.algafood.algafoodapi.api.model.input.PedidoInputDTO;
import com.algafood.algafoodapi.domain.exceptions.EntityNotfoundException;
import com.algafood.algafoodapi.domain.exceptions.NegocioException;
import com.algafood.algafoodapi.domain.models.Pedido;
import com.algafood.algafoodapi.domain.models.Usuario;
import com.algafood.algafoodapi.domain.repository.PedidoRepository;
import com.algafood.algafoodapi.domain.service.CadastroPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoModelAssembler pedidoModel;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModel;

    @Autowired
    private CadastroPedidoService cadastroPedido;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;
    
    @GetMapping
    public List<PedidoResumoDTO> listar() {
        return pedidoResumoModel.toCollectionDTO(pedidoRepository.findAll());
    }

    @GetMapping("/{codigo}")
    public PedidoDTO buscar(@PathVariable String codigo) {
        return pedidoModel.toDTO(cadastroPedido.findOrFail(codigo));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO emitir(@Valid @RequestBody PedidoInputDTO pedidoInput) {
        try {
            Pedido newPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            //TO-DO pegar usuário autenticado
            newPedido.setCliente(new Usuario());
            newPedido.getCliente().setId(1L);
    
            newPedido = cadastroPedido.salvar(newPedido);
    
            return pedidoModel.toDTO(newPedido);
        } catch(EntityNotfoundException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
}
