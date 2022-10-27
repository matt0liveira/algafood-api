package com.algafood.algafoodapi.api.v1.assembler.CozinhaAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.v1.InstanceLink;
import com.algafood.algafoodapi.api.v1.controllers.CozinhaController;
import com.algafood.algafoodapi.api.v1.model.CozinhaModel;
import com.algafood.algafoodapi.domain.models.Cozinha;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstanceLink instanceLink;

    public CozinhaModelAssembler() {
        super(CozinhaController.class, CozinhaModel.class);
    }

    @Override
    public CozinhaModel toModel(Cozinha cozinha) {
        CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(instanceLink.linkToCozinhas());

        return cozinhaModel;
    }
}
