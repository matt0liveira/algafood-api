package com.algafood.algafoodapi.api.v1.controllers;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.algafood.algafoodapi.api.v1.assembler.RestauranteAssembler.RestauranteApenasNomeModelAssembler;
import com.algafood.algafoodapi.api.v1.assembler.RestauranteAssembler.RestauranteInputDisassembler;
import com.algafood.algafoodapi.api.v1.assembler.RestauranteAssembler.RestauranteModelAssembler;
import com.algafood.algafoodapi.api.v1.assembler.RestauranteAssembler.RestauranteResumoModelAssembler;
import com.algafood.algafoodapi.api.v1.model.RestauranteApenasNomeModel;
import com.algafood.algafoodapi.api.v1.model.RestauranteModel;
import com.algafood.algafoodapi.api.v1.model.RestauranteResumoModel;
import com.algafood.algafoodapi.api.v1.model.input.RestauranteInputDTO;
import com.algafood.algafoodapi.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.algafood.algafoodapi.core.security.CheckSecurity;
import com.algafood.algafoodapi.domain.exceptions.EntityNotfoundException;
import com.algafood.algafoodapi.domain.exceptions.NegocioException;
import com.algafood.algafoodapi.domain.models.Restaurante;
import com.algafood.algafoodapi.domain.repository.RestauranteRepository;
import com.algafood.algafoodapi.domain.service.CadastroRestauranteService;
// import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(path = "v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

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

    @Autowired
    private RestauranteResumoModelAssembler restauranteResumoModel;

    @Autowired
    private RestauranteApenasNomeModelAssembler restauranteApenasNomeModel;

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping(params = "projecao")
    public CollectionModel<RestauranteApenasNomeModel> listar(String projecao) {
        List<Restaurante> restaurantes = restauranteRepository.findAll();

        return restauranteApenasNomeModel.toCollectionModel(restaurantes);

    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<RestauranteResumoModel> listar() {
        List<Restaurante> restaurantes = restauranteRepository.findAll();

        return restauranteResumoModel.toCollectionModel(restaurantes);
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping("/{restauranteId}")
    public ResponseEntity<RestauranteModel> buscar(@PathVariable Long restauranteId) {

        Restaurante restaurante = cadastroRestaurante.findOrFail(restauranteId);

        RestauranteModel restauranteDTO = restauranteModelAssembler.toModel(restaurante);

        return ResponseEntity.ok(restauranteDTO);

    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @PostMapping
    public ResponseEntity<RestauranteModel> add(@RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
        try {
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInputDTO);

            RestauranteModel restauranteDTO = restauranteModelAssembler
                    .toModel(cadastroRestaurante.salvar(restaurante));
            return ResponseEntity.status(HttpStatus.CREATED).body(restauranteDTO);
        } catch (EntityNotfoundException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @PutMapping("{restauranteId}")
    public ResponseEntity<RestauranteModel> atualizar(@PathVariable Long restauranteId,
            @RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {

        try {
            // Restaurante restaurante =
            // restauranteInputDisassembler.toDomainObject(restauranteInputDTO);

            Restaurante restauranteCurrent = cadastroRestaurante.findOrFail(restauranteId);

            // BeanUtils.copyProperties(restaurante, restauranteCurrent, "id",
            // "formasPagamento", "endereco", "dataCadastro", "produtos");
            restauranteInputDisassembler.copyToDomainObject(restauranteInputDTO, restauranteCurrent);
            RestauranteModel restauranteDTO = restauranteModelAssembler
                    .toModel(cadastroRestaurante.salvar(restauranteCurrent));
            return ResponseEntity.ok(restauranteDTO);
        } catch (EntityNotfoundException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @DeleteMapping("{restauranteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId) {
        cadastroRestaurante.excluir(restauranteId);
    }

    // @PatchMapping("/{restauranteId}")
    // public ResponseEntity<?> atualizarPatch(@PathVariable Long restauranteId,
    // @RequestBody Map<String, Object> fields, HttpServletRequest request) {

    // Restaurante restauranteCurrent =
    // cadastroRestaurante.findOrFail(restauranteId);

    // this.merge(fields, restauranteCurrent, request);
    // validate(restauranteCurrent, "restaurante");

    // return this.atualizar(restauranteId, restauranteCurrent);
    // }

    // private void validate(Restaurante restauranteCurrent, String objName) {

    // BeanPropertyBindingResult bindingResult = new
    // BeanPropertyBindingResult(restauranteCurrent, objName);

    // validator.validate(restauranteCurrent, bindingResult);

    // if(bindingResult.hasErrors()) {
    // throw new ValidationException(bindingResult);
    // }

    // }

    // private void merge(Map<String, Object> fields, Restaurante
    // restauranteCurrenty, HttpServletRequest request) {

    // ServletServerHttpRequest serverHttpRequest = new
    // ServletServerHttpRequest(request);

    // try {
    // ObjectMapper objectMapper = new ObjectMapper();
    // objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
    // true);
    // Restaurante restaurante = objectMapper.convertValue(fields,
    // Restaurante.class);

    // fields.forEach((name, value) -> {
    // Field field = ReflectionUtils.findField(Restaurante.class, name);
    // field.setAccessible(true);

    // Object newValue = ReflectionUtils.getField(field, restaurante);

    // ReflectionUtils.setField(field, restauranteCurrenty, newValue);
    // });
    // } catch (IllegalArgumentException e) {
    // Throwable rootCause = ExceptionUtils.getRootCause(e);
    // throw new HttpMessageNotReadableException(e.getMessage(), rootCause,
    // serverHttpRequest);
    // }
    // }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping("/por-nome-taxa")
    @Override
    public List<Restaurante> restaurantesPorNomeFrete(String nome, BigDecimal freteInicial, BigDecimal freteFinal) {
        return restauranteRepository.findByNomeAndFrete(nome, freteInicial, freteFinal);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
        cadastroRestaurante.ativar(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
        cadastroRestaurante.inativar(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
        cadastroRestaurante.abrir(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
        cadastroRestaurante.fechar(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativarEmMassa(@RequestBody List<Long> restauranteIds) {
        cadastroRestaurante.ativar(restauranteIds);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativarEmMassa(@RequestBody List<Long> restauranteIds) {
        cadastroRestaurante.inativar(restauranteIds);

        return ResponseEntity.noContent().build();
    }

}