package com.algafood.algafoodapi.api.v1.assembler.FormaPagamentoAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.v1.model.input.FormaPagamentoInputDTO;
import com.algafood.algafoodapi.domain.models.FormaPagamento;

@Component
public class FormaPagamentoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamento toDomainObject(FormaPagamentoInputDTO formaPagamentoInputDTO) {
        return modelMapper.map(formaPagamentoInputDTO, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaPagamentoInputDTO formaPagamentoInputDTO, FormaPagamento formaPagamento) {
        modelMapper.map(formaPagamentoInputDTO, formaPagamento);
    }

}