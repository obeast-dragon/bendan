package com.obeast.auth.support.base;

import com.obeast.auth.constant.BendanOAuth2ErrorConstant;
import com.obeast.auth.exception.OAuth2ScopeException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.Jwt;
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

import javax.annotation.Resource;
import java.security.Principal;
import java.time.Instant;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author wxl
 * Date 2022/10/24 11:44
 * @version 1.0
 * Description: 自定义认证Provider
 */
public abstract class OAuth2BaseAuthenticationProvider<T extends OAuth2BaseAuthenticationToken>
        implements AuthenticationProvider {
    private static final Logger log = LoggerFactory.getLogger(OAuth2BaseAuthenticationProvider.class);

    private static final OAuth2TokenType ID_TOKEN_TOKEN_TYPE = new OAuth2TokenType(OidcParameterNames.ID_TOKEN);

    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";


    /**
     * OAuth2AuthorizationService 方法引入
     */
    private final OAuth2AuthorizationService authorizationService;

    /**
     * token生成
     */
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;


    /**
     * 国际化
     */
    private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public OAuth2BaseAuthenticationProvider(
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator
    ) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.authorizationService = authorizationService;
//        父类构造 子类就不用再次引入这个成员变量了
        this.tokenGenerator = tokenGenerator;
    }

//------------------------- abstract ---------------------------------------


    /**
     * Description: 当provider是否支持当前Token
     *
     * @param authentication the authenticated
     * @return boolean
     * @author wxl
     * Date: 2022/10/24 14:31
     */
    @Override
    public abstract boolean supports(Class<?> authentication);

    /**
     * Description: 从请求参数中创建token
     *
     * @param reqParams the request parameters
     * @return org.springframework.security.authentication.UsernamePasswordAuthenticationToken
     * @author wxl
     * Date: 2022/10/24 14:51
     */
    public abstract UsernamePasswordAuthenticationToken buildAndAuthenticateUsernamePasswordToken(Map<String, Object> reqParams);


    /**
     * Description: 判断当前用户是否支持当前客户端
     *
     * @param registeredClient the registeredClient
     * @author wxl
     * Date: 2022/10/24 14:53
     */
    public abstract void supportClient(RegisteredClient registeredClient);


    @SuppressWarnings("unchecked")
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //向下转行
        T passwordCredentialsAuthenticationToken = (T) authentication;
        OAuth2ClientAuthenticationToken oAuth2ClientAuthentication = getAuthenticatedClientElseThrowInvalidClient(
                passwordCredentialsAuthenticationToken);

        RegisteredClient registeredClient = oAuth2ClientAuthentication.getRegisteredClient();
        assert registeredClient != null;

        // 检验客户端
        supportClient(registeredClient);


        //----scope----
        Set<String> scopes;
        // Default to configured scopes
        if (!CollectionUtils.isEmpty(passwordCredentialsAuthenticationToken.getScopes())) {
            for (String requestedScope : passwordCredentialsAuthenticationToken.getScopes()) {
                if (!registeredClient.getScopes().contains(requestedScope)) {
                    throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE);
                }
            }
            scopes = new LinkedHashSet<>(passwordCredentialsAuthenticationToken.getScopes());
        } else {
            throw new OAuth2ScopeException(BendanOAuth2ErrorConstant.SCOPE_IS_EMPTY);
        }


        Map<String, Object> additionalParameters = passwordCredentialsAuthenticationToken.getAdditionalParameters();
        try {
            //-------UsernamePasswordAuthenticationToken------------
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    this.buildAndAuthenticateUsernamePasswordToken(additionalParameters);

            log.debug("got usernamePasswordAuthenticationToken:  " + usernamePasswordAuthenticationToken);



            //----DefaultOAuth2TokenContext 构建----
            // @formatter:off
            DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                    .registeredClient(registeredClient)
                    .principal(usernamePasswordAuthenticationToken)
                    .providerContext(ProviderContextHolder.getProviderContext())
                    .authorizedScopes(scopes)
                    .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                    .authorizationGrant(passwordCredentialsAuthenticationToken);
            // @formatter:on


            //-------OAuth2Authorization 构建------
            OAuth2Authorization.Builder authorizationBuilder =
                    OAuth2Authorization.withRegisteredClient(registeredClient)
                            .principalName(usernamePasswordAuthenticationToken.getName())
                            .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                            .attribute(OAuth2Authorization.AUTHORIZED_SCOPE_ATTRIBUTE_NAME, scopes)
                            .attribute(Principal.class.getName(), usernamePasswordAuthenticationToken);


            // ----- Access token -----
            OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
            //生成 token
            OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
            if (generatedAccessToken == null) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the access token.", ERROR_URI);
                throw new OAuth2AuthenticationException(error);
            }
            //构建 Access token
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


            // ----- Refresh token -----
            OAuth2RefreshToken refreshToken = null;
            if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
                    // Do not issue refresh token to public client
                    !oAuth2ClientAuthentication.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {

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


            OAuth2Authorization authorization = authorizationBuilder.build();
            this.authorizationService.save(authorization);
            log.debug("Password OAuth2Authorization saved successfully");

            return new OAuth2AccessTokenAuthenticationToken(
                    registeredClient,
                    oAuth2ClientAuthentication,
                    accessToken,
                    refreshToken);
        } catch (Exception ex) {
            log.error("the exception maybe in authenticate:  ", ex);
            throw throwOAuth2AuthenticationException(ex);
        }

    }


    /**
     * Description: 匹配抛出异常
     *
     * @param ex             exception
     * @return org.springframework.security.core.AuthenticationException
     * @author wxl
     * Date: 2022/10/24 16:06
     */
    private AuthenticationException throwOAuth2AuthenticationException(Exception ex) {
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


    /**
     * Description: 获取OAuth2ClientAuthenticationToken
     *
     * @param authentication the authenticated
     * @return org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken
     * @author wxl
     * Date: 2022/10/24 16:06
     */
    private OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient
    (Authentication authentication) {

        OAuth2ClientAuthenticationToken clientAuthenticationToken = null;
        Object principal = authentication.getPrincipal();

        //判断传入客户端是否实现或者与本类相同
        if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(principal.getClass())) {
            clientAuthenticationToken = (OAuth2ClientAuthenticationToken) principal;
        }

        if (clientAuthenticationToken != null && clientAuthenticationToken.isAuthenticated()) {
            return clientAuthenticationToken;
        }


        throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
    }

}
