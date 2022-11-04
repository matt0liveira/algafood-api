package com.algafood.algafoodapi.api.v1.assembler.FormaPagamentoAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.v1.InstanceLink;
import com.algafood.algafoodapi.api.v1.controllers.FormaPagamentoControlller;
import com.algafood.algafoodapi.api.v1.model.FormaPagamentoModel;
import com.algafood.algafoodapi.core.security.SecurityUtils;
import com.algafood.algafoodapi.domain.models.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler
        extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstanceLink instanceLink;

    @Autowired
    private SecurityUtils securityUtils;

    public FormaPagamentoModelAssembler() {
        super(FormaPagamentoControlller.class, FormaPagamentoModel.class);
    }

    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        FormaPagamentoModel formaPagamentoModel = createModelWithId(formaPagamento.getId(), formaPagamento);
        modelMapper.map(formaPagamento, formaPagamentoModel);

        if (securityUtils.podeConsultarFormasPagamento()) {
            formaPagamentoModel.add(instanceLink.linkToFormasPagamentos(IanaLinkRelations.COLLECTION_VALUE));
        }

        return formaPagamentoModel;
    }

    @Override
    public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        CollectionModel<FormaPagamentoModel> collectionModel = super.toCollectionModel(entities);

        if (securityUtils.podeConsultarFormasPagamento()) {
            collectionModel.add(instanceLink.linkToFormasPagamentos(IanaLinkRelations.COLLECTION_VALUE));
        }

        return collectionModel;
    }

}
