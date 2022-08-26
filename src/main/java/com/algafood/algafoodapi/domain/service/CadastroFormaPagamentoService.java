package com.algafood.algafoodapi.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafood.algafoodapi.domain.exceptions.EntityInUseException;
import com.algafood.algafoodapi.domain.exceptions.FormaPagamentoNotfoundException;
import com.algafood.algafoodapi.domain.models.FormaPagamento;
import com.algafood.algafoodapi.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {
    
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    public void excluir(Long formaPagamentoId) {
        try {
            formaPagamentoRepository.deleteById(formaPagamentoId);
            formaPagamentoRepository.flush();
        } catch(EmptyResultDataAccessException e) {
            throw new FormaPagamentoNotfoundException(formaPagamentoId);
        } catch(DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("Forma de pagamento de código %d não pode ser removda, pois está em uso!", formaPagamentoId));
        }
    }

    public FormaPagamento findOrFail(Long formaPagamentoId) {
        return formaPagamentoRepository.findById(formaPagamentoId)
            .orElseThrow(() -> new FormaPagamentoNotfoundException(formaPagamentoId));
    }
}
