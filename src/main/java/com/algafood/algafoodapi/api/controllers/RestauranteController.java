package com.algafood.algafoodapi.api.controllers;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.algafood.algafoodapi.core.validation.ValidationException;
import com.algafood.algafoodapi.domain.exceptions.EntityNotfoundException;
import com.algafood.algafoodapi.domain.exceptions.NegocioException;
import com.algafood.algafoodapi.domain.models.Restaurante;
import com.algafood.algafoodapi.domain.repository.RestauranteRepository;
import com.algafood.algafoodapi.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {
    
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private SmartValidator validator;

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {

        Restaurante restaurante = cadastroRestaurante.findOrFail(restauranteId);
        return ResponseEntity.ok(restaurante);
        
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid Restaurante restaurante) {
        try {          
            cadastroRestaurante.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (EntityNotfoundException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @PutMapping("{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody @Valid Restaurante restaurante) {
        Restaurante restauranteCurrent = cadastroRestaurante.findOrFail(restauranteId);

        try {
            BeanUtils.copyProperties(restaurante, restauranteCurrent, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
            cadastroRestaurante.salvar(restauranteCurrent);
            return ResponseEntity.ok(restauranteCurrent);
        } catch (EntityNotfoundException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("{restauranteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId) {
        cadastroRestaurante.excluir(restauranteId);
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarPatch(@PathVariable Long restauranteId, @RequestBody Map<String, Object> fields, HttpServletRequest request) {

        Restaurante restauranteCurrent = cadastroRestaurante.findOrFail(restauranteId);

        this.merge(fields, restauranteCurrent, request);
        validate(restauranteCurrent, "restaurante");

        return this.atualizar(restauranteId, restauranteCurrent);
    }

    private void validate(Restaurante restauranteCurrent, String objName) {

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restauranteCurrent, objName);

        validator.validate(restauranteCurrent, bindingResult);

        if(bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

    }

    private void merge(Map<String, Object> fields, Restaurante restauranteCurrenty, HttpServletRequest request) {

        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            Restaurante restaurante = objectMapper.convertValue(fields, Restaurante.class);
    
            fields.forEach((name, value) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, name);
                field.setAccessible(true);
    
                Object newValue = ReflectionUtils.getField(field, restaurante);
    
                ReflectionUtils.setField(field, restauranteCurrenty, newValue);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

    @GetMapping("/por-nome-taxa")
    public List<Restaurante> restaurantesPorNomeFrete(String nome, BigDecimal freteInicial, BigDecimal freteFinal) {
        return restauranteRepository.findByNomeAndFrete(nome, freteInicial, freteFinal);
    }

}