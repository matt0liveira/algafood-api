package com.algafood.algafoodapi.api.controllers;

import java.util.List;

import javax.validation.Valid;

// import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.assembler.PedidoAssembler.PedidoInputDisassembler;
import com.algafood.algafoodapi.api.assembler.PedidoAssembler.PedidoModelAssembler;
// import com.algafood.algafoodapi.api.assembler.PedidoAssembler.PedidoResumoModelAssembler;
import com.algafood.algafoodapi.api.model.PedidoDTO;
// import com.algafood.algafoodapi.api.model.PedidoResumoDTO;
import com.algafood.algafoodapi.api.model.input.PedidoInputDTO;
import com.algafood.algafoodapi.domain.exceptions.EntityNotfoundException;
import com.algafood.algafoodapi.domain.exceptions.NegocioException;
import com.algafood.algafoodapi.domain.models.Pedido;
import com.algafood.algafoodapi.domain.models.Usuario;
import com.algafood.algafoodapi.domain.repository.PedidoRepository;
import com.algafood.algafoodapi.domain.repository.filter.PedidoFilter;
import com.algafood.algafoodapi.domain.service.CadastroPedidoService;
// import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
// import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.algafood.algafoodapi.infrastructure.repository.spec.PedidoSpecs;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoModelAssembler pedidoModel;

    // @Autowired
    // private PedidoResumoModelAssembler pedidoResumoModel;

    @Autowired
    private CadastroPedidoService cadastroPedido;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;
    
    //EXEMPLO DE FILTRO DE CAMPOS NA LISTAGEM SEM O USO DE BIBLIOTECAS
    // @GetMapping
    // public MappingJacksonValue listar(@RequestParam(required = false) String fields) {
    //     List<PedidoResumoDTO> pedidoModel = pedidoResumoModel.toCollectionDTO(pedidoRepository.findAll());

    //     MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidoModel);

    //     SimpleFilterProvider filterProvider = new SimpleFilterProvider();
    //     filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());

    //     if(StringUtils.isNotBlank(fields)) {
    //         filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));
    //     }

    //     pedidosWrapper.setFilters(filterProvider);

    //     return pedidosWrapper;
    // }

    @GetMapping
    public List<PedidoDTO> pesquisar(PedidoFilter filter) {
        return pedidoModel.toCollectionDTO(pedidoRepository.findAll(PedidoSpecs.usingFilter(filter)));
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
