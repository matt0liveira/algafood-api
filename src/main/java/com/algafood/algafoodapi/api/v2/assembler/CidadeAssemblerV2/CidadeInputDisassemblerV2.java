package com.algafood.algafoodapi.api.v2.assembler.CidadeAssemblerV2;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.v2.model.input.CidadeInputModelV2;
import com.algafood.algafoodapi.domain.models.Cidade;
import com.algafood.algafoodapi.domain.models.Estado;

@Component
public class CidadeInputDisassemblerV2 {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeInputModelV2 cidadeInputDTO) {
        return modelMapper.map(cidadeInputDTO, Cidade.class);
    }

    public void copyToDomainOject(CidadeInputModelV2 cidadeInputDTO, Cidade cidade) {
        // Para evitar org.hibernate.HibernateException: identifier of an instance of
        // com.algaworks.algafood.domain.model.Estado was altered from 1 to 2
        cidade.setEstado(new Estado());
        modelMapper.map(cidadeInputDTO, cidade);
    }

}
