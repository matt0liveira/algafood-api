package com.algafood.algafoodapi.api.v1.controllers;

import java.util.Map;

import javax.validation.Valid;

// import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
// import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.v1.assembler.PedidoAssembler.PedidoInputDisassembler;
import com.algafood.algafoodapi.api.v1.assembler.PedidoAssembler.PedidoModelAssembler;
import com.algafood.algafoodapi.api.v1.assembler.PedidoAssembler.PedidoResumoModelAssembler;
import com.algafood.algafoodapi.api.v1.model.PedidoModel;
import com.algafood.algafoodapi.api.v1.model.PedidoResumoModel;
import com.algafood.algafoodapi.api.v1.model.input.PedidoInputModel;
import com.algafood.algafoodapi.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.algafood.algafoodapi.core.data.PageWrapper;
import com.algafood.algafoodapi.core.data.PageableTranslator;
import com.algafood.algafoodapi.domain.exceptions.EntityNotfoundException;
import com.algafood.algafoodapi.domain.exceptions.NegocioException;
import com.algafood.algafoodapi.domain.filter.PedidoFilter;
import com.algafood.algafoodapi.domain.models.Pedido;
import com.algafood.algafoodapi.domain.models.Usuario;
import com.algafood.algafoodapi.domain.repository.PedidoRepository;
import com.algafood.algafoodapi.domain.service.CadastroPedidoService;
// import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
// import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.algafood.algafoodapi.infrastructure.repository.spec.PedidoSpecs;

@RestController
@RequestMapping(path = "v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

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

    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    // EXEMPLO DE FILTRO DE CAMPOS NA LISTAGEM SEM O USO DE BIBLIOTECAS
    // @GetMapping
    // public MappingJacksonValue listar(@RequestParam(required = false) String
    // fields) {
    // List<PedidoResumoDTO> pedidoModel =
    // pedidoResumoModel.toCollectionDTO(pedidoRepository.findAll());

    // MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidoModel);

    // SimpleFilterProvider filterProvider = new SimpleFilterProvider();
    // filterProvider.addFilter("pedidoFilter",
    // SimpleBeanPropertyFilter.serializeAll());

    // if(StringUtils.isNotBlank(fields)) {
    // filterProvider.addFilter("pedidoFilter",
    // SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));
    // }

    // pedidosWrapper.setFilters(filterProvider);

    // return pedidosWrapper;
    // }

    @GetMapping
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filter, Pageable pageable) {
        Pageable pageableTranslated = translatePageable(pageable);
        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usingFilter(filter), pageableTranslated);

        pedidosPage = new PageWrapper<>(pedidosPage, pageable);

        PagedModel<PedidoResumoModel> pedidosPagedModel = pagedResourcesAssembler.toModel(pedidosPage,
                pedidoResumoModel);

        return pedidosPagedModel;
    }

    @GetMapping("/{codigo}")
    public PedidoModel buscar(@PathVariable String codigo) {
        return pedidoModel.toModel(cadastroPedido.findOrFail(codigo));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel emitir(@Valid @RequestBody PedidoInputModel pedidoInput) {
        try {
            Pedido newPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            // TO-DO pegar usuário autenticado
            newPedido.setCliente(new Usuario());
            newPedido.getCliente().setId(2L);

            newPedido = cadastroPedido.salvar(newPedido);

            return pedidoModel.toModel(newPedido);
        } catch (EntityNotfoundException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    private Pageable translatePageable(Pageable pageable) {
        var mapping = Map.of(
                "codigo", "codigo",
                "nomeCliente", "cliente.nome",
                "restaurante.nome", "restaurante.nome",
                "valorTotal", "valorTotal");

        return PageableTranslator.translate(pageable, mapping);
    }
}
