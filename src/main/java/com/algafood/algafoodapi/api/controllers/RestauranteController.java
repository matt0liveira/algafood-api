package com.algafood.algafoodapi.api.controllers;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.algafoodapi.api.assembler.RestauranteAssembler.RestauranteInputDisassembler;
import com.algafood.algafoodapi.api.assembler.RestauranteAssembler.RestauranteModelAssembler;
import com.algafood.algafoodapi.api.model.RestauranteDTO;
import com.algafood.algafoodapi.api.model.input.RestauranteInputDTO;
import com.algafood.algafoodapi.domain.exceptions.EntityNotfoundException;
import com.algafood.algafoodapi.domain.exceptions.NegocioException;
import com.algafood.algafoodapi.domain.models.Restaurante;
import com.algafood.algafoodapi.domain.repository.RestauranteRepository;
import com.algafood.algafoodapi.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {
    
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    // @Autowired
    // private SmartValidator validator;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @GetMapping
    public List<RestauranteDTO> listar() {
        return restauranteModelAssembler.toCollectionDTO(restauranteRepository.findAll());
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<RestauranteDTO> buscar(@PathVariable Long restauranteId) {

        Restaurante restaurante = cadastroRestaurante.findOrFail(restauranteId);

        RestauranteDTO restauranteDTO = restauranteModelAssembler.toDTO(restaurante);
        
        return ResponseEntity.ok(restauranteDTO);
        
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
        try {          
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInputDTO);

            RestauranteDTO restauranteDTO = restauranteModelAssembler.toDTO(cadastroRestaurante.salvar(restaurante));
            return ResponseEntity.status(HttpStatus.CREATED).body(restauranteDTO);
        } catch (EntityNotfoundException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @PutMapping("{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
        
        try {
            // Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInputDTO);

            Restaurante restauranteCurrent = cadastroRestaurante.findOrFail(restauranteId);
            
            // BeanUtils.copyProperties(restaurante, restauranteCurrent, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
            restauranteInputDisassembler.copyToDomainObject(restauranteInputDTO, restauranteCurrent);
            RestauranteDTO restauranteDTO = restauranteModelAssembler.toDTO(cadastroRestaurante.salvar(restauranteCurrent));
            return ResponseEntity.ok(restauranteDTO);
        } catch (EntityNotfoundException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("{restauranteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId) {
        cadastroRestaurante.excluir(restauranteId);
    }

    // @PatchMapping("/{restauranteId}")
    // public ResponseEntity<?> atualizarPatch(@PathVariable Long restauranteId, @RequestBody Map<String, Object> fields, HttpServletRequest request) {

    //     Restaurante restauranteCurrent = cadastroRestaurante.findOrFail(restauranteId);

    //     this.merge(fields, restauranteCurrent, request);
    //     validate(restauranteCurrent, "restaurante");

    //     return this.atualizar(restauranteId, restauranteCurrent);
    // }

    // private void validate(Restaurante restauranteCurrent, String objName) {

    //     BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restauranteCurrent, objName);

    //     validator.validate(restauranteCurrent, bindingResult);

    //     if(bindingResult.hasErrors()) {
    //         throw new ValidationException(bindingResult);
    //     }

    // }

    // private void merge(Map<String, Object> fields, Restaurante restauranteCurrenty, HttpServletRequest request) {

    //     ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

    //     try {
    //         ObjectMapper objectMapper = new ObjectMapper();
    //         objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
    //         Restaurante restaurante = objectMapper.convertValue(fields, Restaurante.class);
    
    //         fields.forEach((name, value) -> {
    //             Field field = ReflectionUtils.findField(Restaurante.class, name);
    //             field.setAccessible(true);
    
    //             Object newValue = ReflectionUtils.getField(field, restaurante);
    
    //             ReflectionUtils.setField(field, restauranteCurrenty, newValue);
    //         });
    //     } catch (IllegalArgumentException e) {
    //         Throwable rootCause = ExceptionUtils.getRootCause(e);
    //         throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
    //     }
    // }

    @GetMapping("/por-nome-taxa")
    public List<Restaurante> restaurantesPorNomeFrete(String nome, BigDecimal freteInicial, BigDecimal freteFinal) {
        return restauranteRepository.findByNomeAndFrete(nome, freteInicial, freteFinal);
    }

}