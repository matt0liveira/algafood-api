package com.algafood.algafoodapi.core.springdoc;

import java.util.Arrays;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;

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
				.tags(Arrays.asList(
						new Tag().name("Cidades").description("Gerencia as cidades")));
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
												new ApiResponse()
														.description(
																"Recurso não possui representação que poderia ser aceita pelo consumidor"));

										responses.addApiResponse("500",
												new ApiResponse().description("Erro interno do servidor"));
										break;

									case POST:
										responses.addApiResponse("400",
												new ApiResponse()
														.description("Requisiçãi inválida do lado do consumidor"));

										responses.addApiResponse("500",
												new ApiResponse().description("Erro interno do servidor"));

										break;

									case PUT:
										responses.addApiResponse("400",
												new ApiResponse()
														.description("Requisiçãi inválida do lado do consumidor"));

										responses.addApiResponse("500",
												new ApiResponse().description("Erro interno do servidor"));
										break;

									case DELETE:
										responses.addApiResponse("500",
												new ApiResponse().description("Erro interno do servidor"));

										break;

									default:
										break;
								}
							}));
			;
		};
	}

}