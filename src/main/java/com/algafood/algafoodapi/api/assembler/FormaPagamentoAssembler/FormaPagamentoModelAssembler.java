package com.algafood.algafoodapi.api.assembler.FormaPagamentoAssembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.model.FormaPagamentoDTO;
import com.algafood.algafoodapi.domain.models.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler {
    
    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoDTO toDTO(FormaPagamento formaPagamento) {
        return  modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }

    public List<FormaPagamentoDTO> toCollectionDTO(Collection<FormaPagamento> formasPagamentos) {
        return formasPagamentos.stream()
            .map(formaPagamento -> toDTO(formaPagamento))
            .collect(Collectors.toList());
    }

}
