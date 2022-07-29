package com.algafood.algafoodapi.api.assembler.CidadeAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.model.input.CidadeInputDTO;
import com.algafood.algafoodapi.domain.models.Cidade;
import com.algafood.algafoodapi.domain.models.Estado;

@Component
public class CidadeInputDisassembler {
    
    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeInputDTO cidadeInputDTO) {
        return modelMapper.map(cidadeInputDTO, Cidade.class);
    }

    public void copyToDomainOject(CidadeInputDTO cidadeInputDTO, Cidade cidade) {
        // Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// com.algaworks.algafood.domain.model.Estado was altered from 1 to 2
        cidade.setEstado(new Estado());
        modelMapper.map(cidadeInputDTO, cidade);
    }

}
