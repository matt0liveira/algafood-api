package com.algafood.algafoodapi.api.v1.assembler.CidadeAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algafood.algafoodapi.api.v1.InstanceLink;
import com.algafood.algafoodapi.api.v1.controllers.CidadeController;
import com.algafood.algafoodapi.api.v1.model.CidadeModel;
import com.algafood.algafoodapi.core.security.SecurityUtils;
import com.algafood.algafoodapi.domain.models.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

	public CidadeModelAssembler() {
		super(CidadeController.class, CidadeModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private InstanceLink instanceLink;

	@Autowired
	private SecurityUtils securityUtils;

	@Override
	public CidadeModel toModel(Cidade cidade) {
		CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);

		modelMapper.map(cidade, cidadeModel);

		if (securityUtils.podeConsultarCidades()) {
			cidadeModel.add(instanceLink.linkToCidades(IanaLinkRelations.COLLECTION_VALUE));
		}

		// ESTADO
		if (securityUtils.podeConsultarEstados()) {
			cidadeModel.getEstado().add(instanceLink.linkToEstado(cidadeModel.getEstado().getId()));

			cidadeModel.getEstado().add(instanceLink.linkToEstados(IanaLinkRelations.COLLECTION_VALUE));
		}

		return cidadeModel;
	}

	@Override
	public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
		CollectionModel<CidadeModel> collectionModel = super.toCollectionModel(entities);

		if (securityUtils.podeConsultarCidades()) {
			collectionModel.add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
		}

		return collectionModel;
	}
}
