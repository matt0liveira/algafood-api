// package com.algafood.algafoodapi.core.security.authorizationserver;

// import java.security.KeyPair;
// import java.security.interfaces.RSAPublicKey;
// import java.util.Arrays;

// import javax.sql.DataSource;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.io.Resource;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import
// org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
// import
// org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
// import
// org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
// import
// org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
// import
// org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
// import org.springframework.security.oauth2.provider.CompositeTokenGranter;
// import org.springframework.security.oauth2.provider.TokenGranter;
// import org.springframework.security.oauth2.provider.approval.ApprovalStore;
// import
// org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
// import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
// import org.springframework.security.oauth2.provider.token.TokenStore;
// import
// org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
// import
// org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

// import com.nimbusds.jose.JWSAlgorithm;
// import com.nimbusds.jose.jwk.JWKSet;
// import com.nimbusds.jose.jwk.KeyUse;
// import com.nimbusds.jose.jwk.RSAKey;

// @Configuration
// @EnableAuthorizationServer
// public class AuthorizationServerConfig extends
// AuthorizationServerConfigurerAdapter {

// // @Autowired
// // private PasswordEncoder passwordEncoder;

// @Autowired
// private AuthenticationManager authenticationManager;

// @Autowired
// private UserDetailsService userDetailsService;

// // @Autowired
// // private RedisConnectionFactory redisConnectionFactory;

// @Value("${algafood.jwt.keystore.location}")
// private Resource jwtJksResource;

// @Value("${algafood.jwt.keystore.password}")
// private String jwtKeyStorePass;

// @Value("${algafood.jwt.keystore.keypair-alias}")
// private String jwtKeyPairAlias;

// @Autowired
// private DataSource dataSource;

// @Override
// public void configure(ClientDetailsServiceConfigurer clients) throws
// Exception {
// clients.jdbc(dataSource);

// // CONFIGURANDO CLIENT EM MEMORY
// // clients.inMemory()
// // .withClient("algafood-web")
// // .secret(passwordEncoder.encode("web123"))
// // .authorizedGrantTypes("password", "refresh_token")
// // .scopes("WRITE", "READ")
// // .accessTokenValiditySeconds(60 * 60 * 6) // PADRÃO DE 12H
// // .refreshTokenValiditySeconds(60 * 60 * 24 * 60); // PADRÃO DE 30 DIAS

// }

// @Override
// public void configure(AuthorizationServerEndpointsConfigurer endpoints)
// throws Exception {
// var enhancerChain = new TokenEnhancerChain();
// enhancerChain.setTokenEnhancers(Arrays.asList(new
// JwtCustomClaimsTokenEnhancer(), jwtAccessTokenConverter()));

// endpoints
// .authenticationManager(authenticationManager)
// .userDetailsService(userDetailsService)
// .tokenGranter(tokenGranter(endpoints))
// .accessTokenConverter(jwtAccessTokenConverter())
// .tokenEnhancer(enhancerChain)
// .approvalStore((approvalStore(endpoints.getTokenStore())));
// // .tokenStore(redisTokenStore());
// // .reuseRefreshTokens(false); MÉTODO BOOLEANO QUE DETERMINA A REUTILIZAÇÃO
// DE
// // UM REFRESH_TOKEN
// }

// @Override
// public void configure(AuthorizationServerSecurityConfigurer security) throws
// Exception {
// security
// // .checkTokenAccess("isAuthenticated()");
// .checkTokenAccess("permitAll()")
// .tokenKeyAccess("permitAll()")
// .allowFormAuthenticationForClients();
// }

// private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer
// endpoints) {
// var pkceAuthorizationCodeTokenGranter = new
// PkceAuthorizationCodeTokenGranter(
// endpoints.getTokenServices(),
// endpoints.getAuthorizationCodeServices(),
// endpoints.getClientDetailsService(),
// endpoints.getOAuth2RequestFactory());

// var granters = Arrays.asList(
// pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());

// return new CompositeTokenGranter(granters);
// }

// @Bean
// public JWKSet jwkSet() {
// RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey)
// keyPair().getPublic())
// .keyUse(KeyUse.SIGNATURE)
// .algorithm(JWSAlgorithm.RS256)
// .keyID("algafood-key-id");

// return new JWKSet(builder.build());
// }

// @Bean
// public JwtAccessTokenConverter jwtAccessTokenConverter() {
// var jwtAccessTokenConverter = new JwtAccessTokenConverter();
// // CHAVE SIMÉTRICA
// //
// jwtAccessTokenConverter.setSigningKey("63dcb114-4869-4e1d-bbc0-60028323f6e2");

// jwtAccessTokenConverter.setKeyPair(keyPair());

// return jwtAccessTokenConverter;
// }

// private KeyPair keyPair() {
// var keyStorePass = jwtKeyStorePass;
// var keyPairAlias = jwtKeyPairAlias;

// var keyStoreKeyFactory = new KeyStoreKeyFactory(jwtJksResource,
// keyStorePass.toCharArray());
// var keyPair = keyStoreKeyFactory.getKeyPair(keyPairAlias);
// return keyPair;
// }

// private ApprovalStore approvalStore(TokenStore tokenStore) {
// var approvalStore = new TokenApprovalStore();
// approvalStore.setTokenStore(tokenStore);

// return approvalStore;
// }

// // STATEFUL -> ARMAZENANDO SESSÃO DO USUÁRIO EM BANCO NOSQL
// // private TokenStore redisTokenStore() {
// // return new RedisTokenStore(redisConnectionFactory);
// // }
// }