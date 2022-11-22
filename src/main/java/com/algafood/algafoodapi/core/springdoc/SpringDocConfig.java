package com.algafood.algafoodapi.core.springdoc;

import java.util.HashMap;
import java.util.Map;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
@SecurityScheme(name = "security_auth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(authorizationCode = @OAuthFlow(authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}", tokenUrl = "${springdoc.oAuthFlow.tokenUrl}", scopes = {
		@OAuthScope(name = "READ", description = "read scope"),
		@OAuthScope(name = "WRITE", description = "write scope")
})))
public class SpringDocConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("AlgaFood Api")
						.version("v1")
						.description("REST API do AlgaFood")
						.license(new License()
								.name("Apache 2.0")
								.url("http://springdoc.com")))
				// .tags(Arrays.asList(
				// new Tag().name("Cidades").description("Gerencia as cidades")))
				.components(new Components()
						.schemas(generateSchemas())
						.responses(generateResponses()));
	}

	@Bean
	public OpenApiCustomiser openApiCustomiser() {
		return openapi -> {
			openapi.getPaths()
					.values()
					.forEach(pathItem -> pathItem.readOperationsMap()
							.forEach((httpMethod, operation) -> {
								ApiResponses responses = operation.getResponses();

								switch (httpMethod) {
									case GET:
										responses.addApiResponse("406",
												new ApiResponse().$ref("NotAcceptableResponse"));

										responses.addApiResponse("500",
												new ApiResponse().$ref("InternalServerError"));
										break;

									case POST:
										responses.addApiResponse("400",
												new ApiResponse()
														.$ref("BadRequestResponse"));

										responses.addApiResponse("500",
												new ApiResponse().$ref("InternalServerError"));

										break;

									case PUT:
										responses.addApiResponse("400",
												new ApiResponse()
														.$ref("BadRequestResponse"));

										responses.addApiResponse("500",
												new ApiResponse().$ref("InternalServerError"));
										break;

									case DELETE:
										responses.addApiResponse("500",
												new ApiResponse().$ref("InternalServerError"));

										break;

									default:
										break;
								}
							}));
			;
		};
	}

	private Map<String, Schema> generateSchemas() {
		Map<String, Schema> schemaMap = new HashMap<>();

		Map<String, Schema> problemSchema = ModelConverters.getInstance().read(ErrorApi.class);
		Map<String, Schema> problemObjectSchema = ModelConverters.getInstance().read(ErrorApi.Object.class);

		schemaMap.putAll(problemSchema);
		schemaMap.putAll(problemObjectSchema);

		return schemaMap;
	}

	private Map<String, ApiResponse> generateResponses() {
		Map<String, ApiResponse> apiResponseMap = new HashMap<>();

		Content content = new Content()
				.addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType().schema(new Schema<ErrorApi>().$ref("ErrorApi")));

		apiResponseMap.put("BadRequestResponse", new ApiResponse()
				.description("Requisição inválida")
				.content(content));

		apiResponseMap.put("NotAcceptableResponse", new ApiResponse()
				.description("Recurso não possui representação que poderia ser aceita pelo consumidor")
				.content(content));

		apiResponseMap.put("InternalServerError", new ApiResponse()
				.description("Erro interno do servidor")
				.content(content));

		return apiResponseMap;
	}

}