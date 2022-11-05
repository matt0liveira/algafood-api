package com.algafood.algafoodapi.core.openapi;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.apache.http.HttpStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.algafood.algafoodapi.api.v1.model.CidadeModel;
import com.algafood.algafoodapi.api.v1.model.CozinhaModel;
import com.algafood.algafoodapi.api.v1.model.EstadoModel;
import com.algafood.algafoodapi.api.v1.model.FormaPagamentoModel;
import com.algafood.algafoodapi.api.v1.model.GrupoModel;
import com.algafood.algafoodapi.api.v1.model.PedidoResumoModel;
import com.algafood.algafoodapi.api.v1.model.PermissaoModel;
import com.algafood.algafoodapi.api.v1.model.ProdutoModel;
import com.algafood.algafoodapi.api.v1.model.RestauranteResumoModel;
import com.algafood.algafoodapi.api.v1.model.UsuarioModel;
import com.algafood.algafoodapi.api.v1.openapi.model.CidadesModelOpenApi;
import com.algafood.algafoodapi.api.v1.openapi.model.CozinhasModelOpenApi;
import com.algafood.algafoodapi.api.v1.openapi.model.EstadosModelOpenApi;
import com.algafood.algafoodapi.api.v1.openapi.model.FormasPagamentosModelOpenApi;
import com.algafood.algafoodapi.api.v1.openapi.model.GrupoPermissoesModelOpenApi;
import com.algafood.algafoodapi.api.v1.openapi.model.GruposModelOpenApi;
import com.algafood.algafoodapi.api.v1.openapi.model.LinksModelOpenApi;
import com.algafood.algafoodapi.api.v1.openapi.model.PageableModelOpenApi;
import com.algafood.algafoodapi.api.v1.openapi.model.PedidosModelOpenApi;
import com.algafood.algafoodapi.api.v1.openapi.model.ProdutosModelOpenApi;
import com.algafood.algafoodapi.api.v1.openapi.model.RestaurantesModelOpenApi;
import com.algafood.algafoodapi.api.v1.openapi.model.UsuariosModelOpenApi;
import com.algafood.algafoodapi.api.v2.model.CidadeModelV2;
import com.algafood.algafoodapi.api.v2.model.CozinhaModelV2;
import com.algafood.algafoodapi.api.v2.openapi.model.CidadesModelOpenApiV2;
import com.algafood.algafoodapi.api.v2.openapi.model.CozinhasModelOpenApiV2;
import com.algafood.algafoodapi.api.v2.openapi.model.LinksModelOpenApiV2;
import com.algafood.algafoodapi.api.v2.openapi.model.PageableModelOpenApiV2;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
// import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
// import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.HttpAuthenticationScheme;
// import springfox.documentation.service.ParameterType;
import springfox.documentation.service.Response;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

	@Bean
	public JacksonModuleRegistrar springFoxJacksonConfig() {
		return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
	}

	@Bean
	public Docket apiDocketV1() {
		var typeResolver = new TypeResolver();

		return new Docket(DocumentationType.OAS_30)
				.groupName("V1")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.algafood.algafoodapi.api"))
				.paths(PathSelectors.ant("/v1/**"))
				.build()
				.apiInfo(apiInfoV1())
				.tags(
						new Tag("Cidades", "Gerencia as cidades"),
						new Tag("Grupos", "Gerencia os grupos de permissões"),
						new Tag("Cozinhas", "Gerencia as cozinhas"),
						new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
						new Tag("Pedidos", "Gerencia os pedidos"),
						new Tag("Restaurantes", "Gerencia os restaurantes"),
						new Tag("Estados", "Gerencia os estados"),
						new Tag("Produtos", "Gerencia os produtos de restaurantes"),
						new Tag("Usuários", "Gerencia os usuários"),
						new Tag("Estatísticas", "Gerencia dados estatísticos"),
						new Tag("Permissões", "Gerencia as permissões"))

				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(ErrorApi.class))
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, CozinhaModel.class),
						CozinhasModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, PedidoResumoModel.class),
						PedidosModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(Page.class, PedidoResumoModel.class),
						PedidosModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, CidadeModel.class),
						CidadesModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, EstadoModel.class),
						EstadosModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class),
						FormasPagamentosModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, GrupoModel.class),
						GruposModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, PermissaoModel.class),
						GrupoPermissoesModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, ProdutoModel.class),
						ProdutosModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, RestauranteResumoModel.class),
						RestaurantesModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, UsuarioModel.class),
						UsuariosModelOpenApi.class))
				.ignoredParameterTypes(ServletWebRequest.class)
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(List.of(authenticationScheme()))
				.securityContexts(List.of(securityContext()));
		// .globalRequestParameters(Arrays.asList(
		// new RequestParameterBuilder()
		// .name("fields")
		// .description("Nome das propriedades para filtrar na resposta, separados por
		// vírgula")
		// .in(ParameterType.QUERY)
		// .required(true)
		// .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
		// .build())); CONFIGURANDO PARÂMETROS GLOBAIS
	}

	@Bean
	public Docket apiDocketV2() {
		var typeResolver = new TypeResolver();

		return new Docket(DocumentationType.OAS_30)
				.groupName("V2")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.algafood.algafoodapi.api"))
				.paths(PathSelectors.ant("/v2/**"))
				.build()
				.apiInfo(apiInfoV2())
				.tags(
						new Tag("Cidades", "Gerencia as cidades"),
						new Tag("Cozinhas", "Gerencia as cozinhas"))
				.useDefaultResponseMessages(false)
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, CidadeModelV2.class),
						CidadesModelOpenApiV2.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, CozinhaModelV2.class),
						CozinhasModelOpenApiV2.class))
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(ErrorApi.class))
				.directModelSubstitute(Pageable.class, PageableModelOpenApiV2.class)
				.directModelSubstitute(Links.class, LinksModelOpenApiV2.class)
				.ignoredParameterTypes(ServletWebRequest.class)
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(List.of(authenticationScheme()))
				.securityContexts(List.of(securityContext()));
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(securityReference()).build();
	}

	private List<SecurityReference> securityReference() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return List.of(new SecurityReference("Authorization", authorizationScopes));
	}

	private HttpAuthenticationScheme authenticationScheme() {
		return HttpAuthenticationScheme.JWT_BEARER_BUILDER.name("Authorization").build();
	}

	private List<Response> globalGetResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder()
						.code(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR))
						.description("Erro interno do servidor")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getErrorApiModelReference())
						.build(),
				new ResponseBuilder()
						.code(String.valueOf(HttpStatus.SC_NOT_ACCEPTABLE))
						.description("Recurso não possui representação que poderia ser aceita pelo consumidor")
						.build());
	}

	private List<Response> globalPostResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder()
						.code(String.valueOf(HttpStatus.SC_BAD_REQUEST))
						.description("Requisição inválida (erro do cliente)")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getErrorApiModelReference())
						.build(),

				new ResponseBuilder()
						.code(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR))
						.description("Erro interno do servidor")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getErrorApiModelReference())
						.build(),

				new ResponseBuilder()
						.code(String.valueOf(HttpStatus.SC_NOT_ACCEPTABLE))
						.description("Recurso não possui representação que poderia ser aceita pelo consumidor")
						.build(),

				new ResponseBuilder()
						.code(String.valueOf(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE))
						.description("Corpo da requisição em formato não suportado")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getErrorApiModelReference())
						.build());
	}

	private List<Response> globalPutResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder()
						.code(String.valueOf(HttpStatus.SC_BAD_REQUEST))
						.description("Requisição inválida (erro do cliente)")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getErrorApiModelReference())
						.build(),

				new ResponseBuilder()
						.code(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR))
						.description("Erro interno do servidor")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getErrorApiModelReference())
						.build(),

				new ResponseBuilder()
						.code(String.valueOf(HttpStatus.SC_NOT_ACCEPTABLE))
						.description("Recurso não possui representação que poderia ser aceita pelo consumidor")
						.build(),

				new ResponseBuilder()
						.code(String.valueOf(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE))
						.description("Corpo da requisição em formato não suportado")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getErrorApiModelReference())
						.build());
	}

	private List<Response> globalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder()
						.code(String.valueOf(HttpStatus.SC_BAD_REQUEST))
						.description("Requisição inválida (erro do cliente)")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getErrorApiModelReference())
						.build(),

				new ResponseBuilder()
						.code(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR))
						.description("Erro interno do servidor")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getErrorApiModelReference())
						.build());
	}

	public ApiInfo apiInfoV1() {
		return new ApiInfoBuilder()
				.title("Api Algafood (Depreciada)")
				.description("Documentação de API desenvolvida para restaurantes.")
				.version("1")
				.contact(new Contact("Algafood", "www.algafood.com.br", "contato@algafood.com"))
				.build();
	}

	public ApiInfo apiInfoV2() {
		return new ApiInfoBuilder()
				.title("Api Algafood")
				.description("Documentação de API desenvolvida para restaurantes.")
				.version("2")
				.contact(new Contact("Algafood", "www.algafood.com.br", "contato@algafood.com"))
				.build();
	}

	private Consumer<RepresentationBuilder> getErrorApiModelReference() {
		return r -> r.model(m -> m.name("ErrorApi")
				.referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
						q -> q.name("ErrorApi").namespace(
								"com.algafood.algafoodapi.api.exceptionhandler")))));
	}
}
