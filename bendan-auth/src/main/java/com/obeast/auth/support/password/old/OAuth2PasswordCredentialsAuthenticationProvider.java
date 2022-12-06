package com.obeast.auth.support.password.old;

import com.obeast.auth.constant.BendanOAuth2ErrorConstant;
import com.obeast.auth.exception.OAuth2ScopeException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.ProviderContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.*;

/**
 * @author wxl
 * Date 2022/12/6 10:47
 * @version 1.0
 * Description: password provider
 */
@Slf4j
public class OAuth2PasswordCredentialsAuthenticationProvider implements AuthenticationProvider {
    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";
//    private static final OAuth2TokenType ID_TOKEN_TOKEN_TYPE = new OAuth2TokenType(OidcParameterNames.ID_TOKEN);

    private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    @Setter
    private UserDetailsService userDetailsService;
    @Setter
    private PasswordEncoder passwordEncoder;

    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;


    public OAuth2PasswordCredentialsAuthenticationProvider(
            OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
            OAuth2AuthorizationService authorizationService
    ) {
        Assert.notNull(tokenGenerator, "tokenGenerator不能为空");
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2PasswordCredentialsAuthenticationToken oAuth2PasswordCredentialsAuthenticationToken =
                (OAuth2PasswordCredentialsAuthenticationToken) authentication;

        OAuth2ClientAuthenticationToken clientPrincipal =
                getAuthenticatedClientElseThrowInvalidClient(oAuth2PasswordCredentialsAuthenticationToken);

        try {
            RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
            assert registeredClient != null;
            if (!registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.PASSWORD)) {
                throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
            }

            //------Default to configured scopes----------
            Set<String> authorizedScopes = registeredClient.getScopes();
            Set<String> requestedScopes = oAuth2PasswordCredentialsAuthenticationToken.getScopes();
            if (!CollectionUtils.isEmpty(requestedScopes)) {
                for (String requestedScope : requestedScopes) {
                    if (!registeredClient.getScopes().contains(requestedScope)) {
                        throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE);
                    }
                }
                authorizedScopes = new LinkedHashSet<>(requestedScopes);
            }

            Map<String, Object> additionalParameters = oAuth2PasswordCredentialsAuthenticationToken.getAdditionalParameters();
            //-------UsernamePasswordAuthenticationToken------------
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    buildAndAuthenticateUsernamePasswordToken(additionalParameters);


//        -----------
            // @formatter:off
            DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                    .registeredClient(registeredClient)
                    .principal(usernamePasswordAuthenticationToken)
                    .providerContext(ProviderContextHolder.getProviderContext())
                    .authorizedScopes(authorizedScopes)
                    .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                    .authorizationGrant(oAuth2PasswordCredentialsAuthenticationToken);
            // @formatter:on
//        -----------

            //-------OAuth2Authorization 构建------
            OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
                    .principalName(usernamePasswordAuthenticationToken.getName())
                    .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                    .attribute(OAuth2Authorization.AUTHORIZED_SCOPE_ATTRIBUTE_NAME, authorizedScopes)
                    .attribute(Principal.class.getName(), usernamePasswordAuthenticationToken);

            // ----- Access token -----
            // @formatter:off
            OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
            OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
            if (generatedAccessToken == null) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the access token.", ERROR_URI);
                throw new OAuth2AuthenticationException(error);
            }
            OAuth2AccessToken accessToken = new OAuth2AccessToken(
                    OAuth2AccessToken.TokenType.BEARER,
                    generatedAccessToken.getTokenValue(),
                    generatedAccessToken.getIssuedAt(),
                    generatedAccessToken.getExpiresAt(),
                    tokenContext.getAuthorizedScopes()
            );
            if (generatedAccessToken instanceof ClaimAccessor) {
                authorizationBuilder.token(accessToken, (metadata) ->
                        metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME,
                                ((ClaimAccessor) generatedAccessToken).getClaims()));
            } else {
                authorizationBuilder.accessToken(accessToken);
            }
            // @formatter:on


            // ----- Refresh token -----
            OAuth2RefreshToken refreshToken = null;
            if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
                    // Do not issue refresh token to public client
                    !clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {

                tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
                OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
                if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                    OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                            "The token generator failed to generate the refresh token.", ERROR_URI);
                    throw new OAuth2AuthenticationException(error);
                }
                refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
                authorizationBuilder.refreshToken(refreshToken);
            }


//            // ----- ID token -----
//            OidcIdToken idToken;
//            if (authorizedScopes.contains(OidcScopes.OPENID)) {
//                // @formatter:off
//                tokenContext = tokenContextBuilder
//                        .tokenType(ID_TOKEN_TOKEN_TYPE)
//                        .authorization(authorizationBuilder.build())    // ID token customizer may need access to the access token and/or refresh token
//                        .build();
//                // @formatter:on
//                OAuth2Token generatedIdToken = this.tokenGenerator.generate(tokenContext);
//                if (!(generatedIdToken instanceof Jwt)) {
//                    OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
//                            "The token generator failed to generate the ID token.", ERROR_URI);
//                    throw new OAuth2AuthenticationException(error);
//                }
//                idToken = new OidcIdToken(generatedIdToken.getTokenValue(), generatedIdToken.getIssuedAt(),
//                        generatedIdToken.getExpiresAt(), ((Jwt) generatedIdToken).getClaims());
//                authorizationBuilder.token(idToken, (metadata) ->
//                        metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, idToken.getClaims()));
//            } else {
//                idToken = null;
//            }
//            Map<String, Object> add = Collections.emptyMap();
//            if (idToken != null) {
//                add = new HashMap<>();
//                add.put(OidcParameterNames.ID_TOKEN, idToken.getTokenValue());
//            }


            OAuth2Authorization authorization = authorizationBuilder.build();
            this.authorizationService.save(authorization);
            log.debug("Password OAuth2Authorization saved successfully");

            return new OAuth2AccessTokenAuthenticationToken(
                    registeredClient,
                    clientPrincipal,
                    accessToken,
                    refreshToken
//                    , add
            );
        } catch (AuthenticationException ex) {
            log.error("the exception maybe in authenticate:  ", ex);
            throw throwOAuth2AuthenticationException(ex);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2PasswordCredentialsAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(Authentication authentication) {

        OAuth2ClientAuthenticationToken clientPrincipal = null;

        if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getPrincipal().getClass())) {
            clientPrincipal = (OAuth2ClientAuthenticationToken) authentication.getPrincipal();
        }

        if (clientPrincipal != null && clientPrincipal.isAuthenticated()) {
            return clientPrincipal;
        }

        throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
    }


    private UsernamePasswordAuthenticationToken buildAndAuthenticateUsernamePasswordToken(Map<String, Object> reqParams) {
        String username = (String) reqParams.get(OAuth2ParameterNames.USERNAME);
        String password = (String) reqParams.get(OAuth2ParameterNames.PASSWORD);
        if (!StringUtils.hasText(password) || !StringUtils.hasText(username)) {
            log.error("密码或者用户名不能为空");
            throw new BadCredentialsException("username or password are must be not null");
        }
        UserDetails user = verifyUserDetails(username, password);
//        todo 限制一个用户登录多端最大个数
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());

    }


    /**
     * Description: 校验用户详情
     * @author wxl
     * Date: 2022/12/6 11:10
     * @param username username
     * @param password  password
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    private UserDetails verifyUserDetails(String username, String password) throws AuthenticationException {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            log.error("找不到用户: {}", username);
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        if (!userDetails.isAccountNonLocked()) {
            log.debug("Failed to authenticate since user account is locked");
            throw new LockedException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"));
        }
        if (!userDetails.isEnabled()) {
            log.debug("Failed to authenticate since user account is disabled");
            throw new DisabledException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"));
        }
        if (!userDetails.isAccountNonExpired()) {
            log.debug("Failed to authenticate since user account has expired");
            throw new AccountExpiredException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"));
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            log.error("密码不匹配: {}", password);
            log.error("密码不匹配: {}", userDetails.getPassword());
            throw new BadCredentialsException("Bad credentials");
        }
        return userDetails;
    }


    /**
     * Description: 补充匹配抛出异常
     *
     * @param ex exception
     * @return org.springframework.security.core.AuthenticationException
     * @author wxl
     * Date: 2022/10/24 16:06
     */
    private AuthenticationException throwOAuth2AuthenticationException(AuthenticationException ex) {
        return switch (ex) {
            case UsernameNotFoundException ignored -> new OAuth2AuthenticationException(
                    new OAuth2Error(BendanOAuth2ErrorConstant.USERNAME_NOT_FOUND,
                            this.messages.getMessage("JdbcDaoImpl.notFound",
                                    "Username not found"),
                            ""));

            case BadCredentialsException ignored -> new OAuth2AuthenticationException(
                    new OAuth2Error(BendanOAuth2ErrorConstant.BAD_CREDENTIALS, this.messages.getMessage(
                            "AbstractUserDetailsAuthenticationProvider.badCredentials",
                            "Bad credentials"), ""));

            case LockedException ignored -> new OAuth2AuthenticationException(
                    new OAuth2Error(BendanOAuth2ErrorConstant.USER_LOCKED, this.messages
                            .getMessage("AbstractUserDetailsAuthenticationProvider.locked",
                                    "User account is locked"), ""));

            case DisabledException ignored -> new OAuth2AuthenticationException(
                    new OAuth2Error(BendanOAuth2ErrorConstant.USER_DISABLE,
                            this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"),
                            ""));

            case AccountExpiredException ignored -> new OAuth2AuthenticationException(
                    new OAuth2Error(BendanOAuth2ErrorConstant.USER_EXPIRED, this.messages
                            .getMessage("AbstractUserDetailsAuthenticationProvider.expired",
                                    "User account has expired"), ""));

            case CredentialsExpiredException ignored -> new OAuth2AuthenticationException(
                    new OAuth2Error(BendanOAuth2ErrorConstant.CREDENTIALS_EXPIRED,
                            this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired",
                                    "User credentials have expired"),
                            ""));

            case OAuth2ScopeException ignored -> new OAuth2AuthenticationException(
                    new OAuth2Error(BendanOAuth2ErrorConstant.INVALID_SCOPE,
                            this.messages.getMessage("AbstractAccessDecisionManager.accessDenied",
                                    "invalid_scope"), ""));

            case InternalAuthenticationServiceException ignored -> new OAuth2AuthenticationException(
                    new OAuth2Error(BendanOAuth2ErrorConstant.BAD_PASSWORD,
                            "bad_password",
                            ""));

            default -> new OAuth2AuthenticationException(BendanOAuth2ErrorConstant.UN_KNOW_LOGIN_ERROR);
        };
    }

}
