package com.algafood.algafoodapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algafood.algafoodapi.domain.exceptions.CozinhaNotfoundException;
import com.algafood.algafoodapi.domain.models.Cozinha;
import com.algafood.algafoodapi.domain.repository.CozinhaRepository;
import com.algafood.algafoodapi.domain.service.CadastroCozinhaService;
import com.algafood.algafoodapi.util.DatabaseCleaner;
import com.algafood.algafoodapi.util.ResourceUtils;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
class RegisterCozinhaIT {

	private static final int ID_NON_EXISTS = 100;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@LocalServerPort
	private int port;

	private List<Cozinha> cozinhas = new ArrayList<>();

	private String jsonCozinha;

	private Cozinha cozinha2 = new Cozinha();

	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		databaseCleaner.clearTables();
		prepareData();

		jsonCozinha = ResourceUtils.getContentFromResource("/json/test-cozinha.json");
	}

	// TESTES DE INTEGRAÇÃO

	@Test
	public void shouldSuccess_WhenDataToBeCorrectly() {

		//cenário
		Cozinha newCozinha = new Cozinha();
		newCozinha.setNome("Chinesa");

		//ação
		newCozinha = cadastroCozinha.salvar(newCozinha);

		//validação
		assertThat(newCozinha).isNotNull();
		assertThat(newCozinha.getId()).isNotNull();

	}

	@Test
	public void shouldFail_WhenWithoutName() {

		assertThrows(ConstraintViolationException.class, () -> {
			//cenário
			Cozinha newCozinha = new Cozinha();
			newCozinha.setNome(null);
	
			//ação
			newCozinha = cadastroCozinha.salvar(newCozinha);
		});

	}

	// @Test
	// public void shouldFail_WhenDeleteCozinhaInUse() {
	// 	assertThrows(EntityInUseException.class, () -> {
	// 		cadastroCozinha.remover(1L);
	// 	});
	// }

	@Test
	public void shouldFail_WhenDeleteCozinhaNonExistent() {

		assertThrows(CozinhaNotfoundException.class, () -> {
			
			cadastroCozinha.remover(100L);

		});

	}

	// TESTE DE API

	@Test	
	public void shouldReturnStatus200_WhenConsultCozinha() {
		/*COMO SE LER ESSE BLOCO DE CÓDIGO
		 * DADO QUE (GIVEN)
		 * 	
		 * QUANDO (WHEN) O VERBO DA REQUISIÇÃO FOR
		 * 
		 * ENTÃO (THEN) VALIDE TAL COISA
		 * 
		*/

		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void shouldReturn201_WhenRegisterCozinha() {
		given()
			.body(jsonCozinha)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void shouldContainNumberCozinhasList_WhenConsultCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(cozinhas.size()));
			// .body("nome", Matchers.hasItems("Tailandesa", "Indina"));
	}

	@Test
	public void shoulReturnResponseAndStatusCorrect_WhenConsultCozinhaExisting() {
		given()
			.pathParam("cozinhaId", 2)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", Matchers.equalTo(cozinha2.getNome()));
	}	

	@Test
	public void shoulReturnStatus404_WhenConsultCozinhaNonexists() {
		given()
			.pathParam("cozinhaId", ID_NON_EXISTS)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	public void prepareData() {
		Cozinha newCozinha1 = new Cozinha();
		newCozinha1.setNome("Italiana");
		cozinhaRepository.save(newCozinha1);
		cozinhas.add(newCozinha1);

		cozinha2.setNome("Brasileira");
		cozinhaRepository.save(cozinha2);
		cozinhas.add(cozinha2);

		Cozinha newCozinha3 = new Cozinha();
		newCozinha3.setNome("Francesa");
		cozinhaRepository.save(newCozinha3);
		cozinhas.add(newCozinha3);
	}

}