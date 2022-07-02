package com.algafood.algafoodapi;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algafood.algafoodapi.domain.models.Cozinha;
import com.algafood.algafoodapi.domain.models.Restaurante;
import com.algafood.algafoodapi.domain.repository.CozinhaRepository;
import com.algafood.algafoodapi.domain.repository.RestauranteRepository;
import com.algafood.algafoodapi.util.DatabaseCleaner;
import com.algafood.algafoodapi.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class RegisterRestauranteIT {
    
    @LocalServerPort
    private int port;

    private String jsonRestaurante;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private List<Restaurante> restaurantes = new ArrayList<>();

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes";

        databaseCleaner.clearTables();
        prepareData();

        jsonRestaurante = ResourceUtils.getContentFromResource("/json/test-restaurante.json");
    }

    @Test
    public void shouldReturnStatus201_WhenRegisterRestaurante() {
        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(jsonRestaurante)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldContainNumberRestaurantesList_WhenConsultRestaurantes() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("", Matchers.hasSize(restaurantes.size()));
    }

    public void prepareData() {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Cozinha teste");
        cozinhaRepository.save(cozinha);

        Restaurante newRestaurante1 = new Restaurante();
        newRestaurante1.setNome("Teste");
        newRestaurante1.setTaxaFrete(BigDecimal.valueOf(12));
        newRestaurante1.setCozinha(cozinha);
        restauranteRepository.save(newRestaurante1);
        restaurantes.add(newRestaurante1);

        Restaurante newRestaurante2 = new Restaurante();
        newRestaurante2.setNome("Teste 2");
        newRestaurante2.setTaxaFrete(BigDecimal.valueOf(6));
        newRestaurante2.setCozinha(cozinha);
        restauranteRepository.save(newRestaurante2);
        restaurantes.add(newRestaurante2);
    }

}
