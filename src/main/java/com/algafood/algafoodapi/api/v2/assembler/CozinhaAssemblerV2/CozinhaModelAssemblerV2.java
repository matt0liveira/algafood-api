package com.algafood.algafoodapi.api.v2.assembler.CozinhaAssemblerV2;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.v2.InstanceLinkV2;
import com.algafood.algafoodapi.api.v2.controllers.CozinhaControllerV2;
import com.algafood.algafoodapi.api.v2.model.CozinhaModelV2;
import com.algafood.algafoodapi.domain.models.Cozinha;

@Component
public class CozinhaModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModelV2> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstanceLinkV2 instanceLink;

    public CozinhaModelAssemblerV2() {
        super(CozinhaControllerV2.class, CozinhaModelV2.class);
    }

    @Override
    public CozinhaModelV2 toModel(Cozinha cozinha) {
        CozinhaModelV2 cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(instanceLink.linkToCozinhas());

        return cozinhaModel;
    }
}
