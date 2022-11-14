package com.algafood.algafoodapi.core.security.authorizationserver;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.algafood.algafoodapi.domain.models.Usuario;
import com.algafood.algafoodapi.domain.repository.UsuarioRepository;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class AuthorizationServerConfig {

	@Autowired
	private JwtKeyStoreProperties jwtKeyStoreProperties;

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain authFilterChain(HttpSecurity http, JdbcOperations jdbcOperations) throws Exception {
		OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer<>();

		authorizationServerConfigurer.authorizationEndpoint(customizer -> customizer.consentPage("/oauth2/consent"));

		RequestMatcher endpointsMatcher = authorizationServerConfigurer
				.getEndpointsMatcher();

		http
				.requestMatcher(endpointsMatcher)
				.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
				.csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
				.apply(authorizationServerConfigurer);

		return http
				.formLogin(customizer -> customizer.loginPage("/login"))
				.build();
	}

	@Bean
	public ProviderSettings providerSettings(AlgaFoodSecurityProperties properties) {
		return ProviderSettings
				.builder()
				.issuer(properties.getProviderUrl())
				.build();
	}

	@Bean
	public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder,
			JdbcOperations jdbcOperations) {

		return new JdbcRegisteredClientRepository(jdbcOperations);
	}

	@Bean
	public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcOperations jdbcOperations,
			RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
	}

	@Bean
	public JWKSource<SecurityContext> jwkSource() throws Exception {
		KeyStore keyStore = KeyStore.getInstance("JKS");
		var keyStorePass = jwtKeyStoreProperties.getPassword().toCharArray();
		InputStream inputStream = jwtKeyStoreProperties.getJksLocation().getInputStream();

		keyStore.load(inputStream, keyStorePass);

		RSAKey rsaKey = RSAKey.load(keyStore, jwtKeyStoreProperties.getKeypairAlias(),
				keyStorePass);

		return new ImmutableJWKSet<>(new JWKSet(rsaKey));
	}

	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(UsuarioRepository usuarioRepository) {
		return context -> {
			Authentication auth = context.getPrincipal();
			if (auth.getPrincipal() instanceof User) {
				User user = (User) auth.getPrincipal();

				Usuario usuario = usuarioRepository.findByEmail(user.getUsername()).orElseThrow();

				Set<String> authorities = new HashSet<>();
				for (GrantedAuthority authority : user.getAuthorities()) {
					authorities.add(authority.getAuthority());
				}

				context.getClaims().claim("user_id", usuario.getId());
				context.getClaims().claim("user_full_name", usuario.getNome());
				context.getClaims().claim("authorities", authorities);
			}
		};
	}

	@Bean
	public OAuth2AuthorizationConsentService consentService(JdbcOperations jdbcOperations,
			RegisteredClientRepository clientRepository) {
		return new JdbcOAuth2AuthorizationConsentService(jdbcOperations, clientRepository);
	}

	@Bean
	public OAuth2AuthorizationQueryService auth2AuthorizationQueryService(JdbcOperations jdbcOperations,
			RegisteredClientRepository repository) {
		return new JdbcOAuth2AuthorizationQueryService(jdbcOperations, repository);
	}

}