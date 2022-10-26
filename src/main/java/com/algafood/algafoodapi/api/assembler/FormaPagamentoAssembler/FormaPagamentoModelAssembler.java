package com.algafood.algafoodapi.api.assembler.FormaPagamentoAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.InstanceLink;
import com.algafood.algafoodapi.api.controllers.FormaPagamentoControlller;
import com.algafood.algafoodapi.api.model.FormaPagamentoModel;
import com.algafood.algafoodapi.domain.models.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler
        extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstanceLink instanceLink;

    public FormaPagamentoModelAssembler() {
        super(FormaPagamentoControlller.class, FormaPagamentoModel.class);
    }

    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        FormaPagamentoModel formaPagamentoModel = createModelWithId(formaPagamento.getId(), formaPagamento);
        modelMapper.map(formaPagamento, formaPagamentoModel);

        formaPagamentoModel.add(instanceLink.linkToFormasPagamentos(IanaLinkRelations.COLLECTION_VALUE));

        return formaPagamentoModel;
    }

    @Override
    public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities)
                .add(instanceLink.linkToFormasPagamentos(IanaLinkRelations.COLLECTION_VALUE));
    }

}
