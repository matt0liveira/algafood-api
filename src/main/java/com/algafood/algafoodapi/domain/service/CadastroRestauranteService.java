package com.algafood.algafoodapi.domain.service;

import com.algafood.algafoodapi.domain.exceptions.EntityInUseException;
import com.algafood.algafoodapi.domain.exceptions.RestauranteNotfoundException;
import com.algafood.algafoodapi.domain.models.Cidade;
import com.algafood.algafoodapi.domain.models.Cozinha;
import com.algafood.algafoodapi.domain.models.FormaPagamento;
import com.algafood.algafoodapi.domain.models.Restaurante;
import com.algafood.algafoodapi.domain.repository.RestauranteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroRestauranteService {
    
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    private static final String ENTITY_IN_USE_MSG = "Restaurante de código %d não pode ser removido, pois está em uso!";

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Cozinha cozinha = cadastroCozinha.findOrFail(restaurante.getCozinha().getId());
        restaurante.setCozinha(cozinha);

        Long cidadeId = restaurante.getEndereco().getCidade().getId();
        Cidade cidade = cadastroCidade.findOrFail(cidadeId);
        restaurante.getEndereco().setCidade(cidade);
        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void excluir(Long restauranteId) {
        try {
            restauranteRepository.deleteById(restauranteId);
            restauranteRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNotfoundException(restauranteId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(ENTITY_IN_USE_MSG, restauranteId));
        }
    }

    @Transactional
    public void ativar(Long restauranteId) {
        Restaurante restaurante = findOrFail(restauranteId);

        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long restauranteId) {
        Restaurante restaurante = findOrFail(restauranteId);

        restaurante.inativar();
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = findOrFail(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamento.findOrFail(formaPagamentoId);

        restaurante.desassociarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = findOrFail(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamento.findOrFail(formaPagamentoId);

        restaurante.associarFormaPagamento(formaPagamento);

    }

    public Restaurante findOrFail(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
            .orElseThrow(() -> new RestauranteNotfoundException(restauranteId));
    }

}