package com.algafood.algafoodapi.api.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.v1.InstanceLink;

@RestController
@RequestMapping(path = "v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private InstanceLink instanceLink;

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(instanceLink.linkToCozinhas("cozinhas"));
        rootEntryPointModel.add(instanceLink.linkToPedidos("pedidos"));
        rootEntryPointModel.add(instanceLink.linkToRestaurantes("restaurantes"));
        rootEntryPointModel.add(instanceLink.linkToGrupos("grupos"));
        rootEntryPointModel.add(instanceLink.linkToUsuarios("usuarios"));
        rootEntryPointModel.add(instanceLink.linkToPermissoes("permissoes"));
        rootEntryPointModel.add(instanceLink.linkToFormasPagamentos("formas-pagamentos"));
        rootEntryPointModel.add(instanceLink.linkToEstados("estados"));
        rootEntryPointModel.add(instanceLink.linkToCidades("cidades"));
        rootEntryPointModel.add(instanceLink.linkToEstatisticas("estatisticas"));

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }

}
