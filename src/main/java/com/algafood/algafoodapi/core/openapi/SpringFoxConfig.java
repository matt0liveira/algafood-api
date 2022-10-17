package com.algafood.algafoodapi.core.openapi;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.apache.http.HttpStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.algafood.algafoodapi.api.exceptionhandler.ErrorApi;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

    @Bean
    public JacksonModuleRegistrar springFoxJacksonConfig() {
        return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
    }

    @Bean
    public Docket apiDocket() {
        var typeResolver = new TypeResolver();

        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.algafood.algafoodapi.api"))
                .build()
                .apiInfo(apiInfo())
                .tags(new Tag("Cidades", "Gerencia as cidades"))
                .tags(new Tag("Grupos", "Gerencia os grupos de permissões"))
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(ErrorApi.class));
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

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Api Algafood")
                .description("Documentação de API desenvolvida para restaurantes.")
                .version("1")
                .contact(new Contact("Algafood", "www.algafood.com.br", "contato@algafood.com"))
                .build();
    }

    private Consumer<RepresentationBuilder> getErrorApiModelReference() {
        return r -> r.model(m -> m.name("ErrorApi")
                .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
                        q -> q.name("ErrorApi").namespace("com.algafood.algafoodapi.api.exceptionhandler")))));
    }
}
