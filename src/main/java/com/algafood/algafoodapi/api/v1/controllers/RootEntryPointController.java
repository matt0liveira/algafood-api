package com.algafood.algafoodapi.api.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.v1.InstanceLink;
import com.algafood.algafoodapi.core.security.SecurityUtils;

@RestController
@RequestMapping(path = "v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private InstanceLink instanceLink;

    @Autowired
    private SecurityUtils securityUtils;

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        if (securityUtils.podeConsultarCidades())
            rootEntryPointModel.add(instanceLink.linkToCozinhas("cozinhas"));

        if (securityUtils.podePesquisarPedidos())
            rootEntryPointModel.add(instanceLink.linkToPedidos("pedidos"));

        if (securityUtils.podeConsultarRestaurantes())
            rootEntryPointModel.add(instanceLink.linkToRestaurantes("restaurantes"));

        if (securityUtils.podeEditarUsuariosGruposPermissoes()) {
            rootEntryPointModel.add(instanceLink.linkToGrupos("grupos"));
            rootEntryPointModel.add(instanceLink.linkToUsuarios("usuarios"));
            rootEntryPointModel.add(instanceLink.linkToPermissoes("permissoes"));
        }

        if (securityUtils.podeConsultarFormasPagamento())
            rootEntryPointModel.add(instanceLink.linkToFormasPagamentos("formas-pagamentos"));

        if (securityUtils.podeConsultarEstados())
            rootEntryPointModel.add(instanceLink.linkToEstados("estados"));

        if (securityUtils.podeConsultarCidades())
            rootEntryPointModel.add(instanceLink.linkToCidades("cidades"));

        if (securityUtils.podeConsultarEstatisticas())
            rootEntryPointModel.add(instanceLink.linkToEstatisticas("estatisticas"));

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }

}
