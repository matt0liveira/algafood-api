package com.algafood.algafoodauth.core;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    // @Autowired
    // private RedisConnectionFactory redisConnectionFactory;

    @Value("${algafood.jwt.keystore.path}")
    private String jwtJksResource;

    @Value("${algafood.jwt.keystore.password}")
    private String jwtKeyStorePass;

    @Value("${algafood.jwt.keystore.keypair-alias}")
    private String jwtKeyPairAlias;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // CONFIGURANDO CLIENT
        clients.inMemory()
                .withClient("algafood-web")
                .secret(passwordEncoder.encode("web123"))
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("WRITE", "READ")
                .accessTokenValiditySeconds(60 * 60 * 6) // PADRÃO DE 12H
                .refreshTokenValiditySeconds(60 * 60 * 24 * 60) // PADRÃO DE 30 DIAS

                .and()
                .withClient("faturamento")
                .secret(passwordEncoder.encode("faturamento123"))
                .authorizedGrantTypes("client_credentials")
                .scopes("WRITE", "READ")

                .and()
                .withClient("foodanalytics")
                .secret(passwordEncoder.encode("food123"))
                .authorizedGrantTypes("authorization_code")
                .scopes("WRITE", "READ")
                .redirectUris("http://127.0.0.1:5500/", "http://aplicacao-cliente")

                .and()
                .withClient("web")
                .authorizedGrantTypes("implicit")
                .scopes("WRITE", "READ")
                .redirectUris("http://aplicacao-cliente")

                .and()
                .withClient("checktoken")
                .secret(passwordEncoder.encode("check123"));

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        var enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(new JwtCustomClaimsTokenEnhancer(), jwtAccessTokenConverter()));

        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenGranter(tokenGranter(endpoints))
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(enhancerChain)
                .approvalStore((approvalStore(endpoints.getTokenStore())));
        // .tokenStore(redisTokenStore());
        // .reuseRefreshTokens(false); MÉTODO BOOLEANO QUE DETERMINA A REUTILIZAÇÃO DE
        // UM REFRESH_TOKEN
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // .checkTokenAccess("isAuthenticated()");
                .checkTokenAccess("permitAll()")
                .tokenKeyAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(
                endpoints.getTokenServices(),
                endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory());

        var granters = Arrays.asList(
                pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());

        return new CompositeTokenGranter(granters);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        var jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // CHAVE SIMÉTRICA
        // jwtAccessTokenConverter.setSigningKey("63dcb114-4869-4e1d-bbc0-60028323f6e2");

        // CHAVE ASSIMÉTRICA (PÚBLICA E PRIVADA)
        var jksResource = new ClassPathResource(jwtJksResource);
        var keyStorePass = jwtKeyStorePass;
        var keyPairAlias = jwtKeyPairAlias;

        var keyStoreKeyFactory = new KeyStoreKeyFactory(jksResource, keyStorePass.toCharArray());
        var keyPair = keyStoreKeyFactory.getKeyPair(keyPairAlias);

        jwtAccessTokenConverter.setKeyPair(keyPair);

        return jwtAccessTokenConverter;
    }

    private ApprovalStore approvalStore(TokenStore tokenStore) {
        var approvalStore = new TokenApprovalStore();
        approvalStore.setTokenStore(tokenStore);

        return approvalStore;
    }

    // STATEFUL -> ARMAZENANDO SESSÃO DO USUÁRIO EM BANCO NOSQL
    // private TokenStore redisTokenStore() {
    // return new RedisTokenStore(redisConnectionFactory);
    // }
}