package com.obeast.auth.support.base;

import com.obeast.auth.constant.OAuth2ErrorConstant;
import com.obeast.auth.exception.OAuth2ScopeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.*;
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

    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";


    /**
     * OAuth2AuthorizationService 方法引入
     * */
    private final OAuth2AuthorizationService authorizationService;

    /**
     * token生成
     * */
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    /**
     * 认证 Manager
     * */
    private final AuthenticationManager authenticationManager;


    @Deprecated
    private Supplier<String> refreshTokenGenerator;

    @Deprecated
    public void setRefreshTokenGenerator(Supplier<String> refreshTokenGenerator) {
        Assert.notNull(refreshTokenGenerator, "refreshTokenGenerator cannot be null");
        this.refreshTokenGenerator = refreshTokenGenerator;
    }

//    /**
//     * 国际化
//     * */
//    private final MessageSourceAccessor messages;

    public OAuth2BaseAuthenticationProvider(
            AuthenticationManager authenticationManager,
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator
//            , MessageSourceAccessor messages
    ) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
        this.authenticationManager = authenticationManager;
//        this.messages = new MessageSourceAccessor(SpringUtil.getBean("securityMessageSource"), Locale.CHINA);
    }

//------------------------- abstract ---------------------------------------


    /**
     * Description: 当provider是否支持当前Token
     * @author wxl
     * Date: 2022/10/24 14:31
     * @param authentication the authenticated
     * @return boolean
     */
    @Override
    public abstract boolean supports(Class<?> authentication);

    /**
     * Description: 从请求参数中创建token
     * @author wxl
     * Date: 2022/10/24 14:51
     * @param reqParams the request parameters
     * @return org.springframework.security.authentication.UsernamePasswordAuthenticationToken
     */
    public abstract UsernamePasswordAuthenticationToken buildCustomizeToken(Map<String, Object> reqParams);


    /**
     * Description: 判断当前用户是否支持当前客户端
     * @author wxl
     * Date: 2022/10/24 14:53
     * @param registeredClient the registeredClient
     */
    public abstract void supportClient(RegisteredClient registeredClient) ;



    @SuppressWarnings("unchecked")
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        T customizeBaseAuthentication  = (T) authentication;
        OAuth2ClientAuthenticationToken oAuth2ClientAuthentication = getOAuth2ClientAuthentication(
                customizeBaseAuthentication);

        RegisteredClient registeredClient = oAuth2ClientAuthentication.getRegisteredClient();

        // 检验客户端
        supportClient(registeredClient);


        //----scope----
        Set<String> scopes;
        if (!CollectionUtils.isEmpty(scopes = customizeBaseAuthentication.getScopes())){
            for (String requestedScope : scopes) {
                if (!registeredClient.getScopes().contains(requestedScope)){
                    throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE);
                }
            }
            scopes = new LinkedHashSet<>(scopes);
        }
        else {
            throw new OAuth2ScopeException(OAuth2ErrorConstant.SCOPE_IS_EMPTY);
        }

        Map<String, Object> additionalParameters = customizeBaseAuthentication.getAdditionalParameters();
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = buildCustomizeToken(additionalParameters);

            log.debug("got usernamePasswordAuthenticationToken:  " + usernamePasswordAuthenticationToken);

            Authentication usernamePasswordAuthentication = authenticationManager
                    .authenticate(usernamePasswordAuthenticationToken);

            //----DefaultOAuth2TokenContext 构建----
            // @formatter:off
            DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                    .registeredClient(registeredClient)
                    .principal(usernamePasswordAuthentication)
                    .providerContext(ProviderContextHolder.getProviderContext())
                    .authorizedScopes(scopes)
                    .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                    .authorizationGrant(customizeBaseAuthentication);
            // @formatter:on


            //-------OAuth2Authorization 构建------
            OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization
                    .withRegisteredClient(registeredClient)
                    .principalName(usernamePasswordAuthentication.getName())
                    .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                    .attribute(OAuth2Authorization.AUTHORIZED_SCOPE_ATTRIBUTE_NAME, scopes);


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
                authorizationBuilder
                        .id(accessToken.getTokenValue())
                        .token(accessToken, (metadata) ->
                                metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME,
                                ((ClaimAccessor) generatedAccessToken).getClaims())
                        )
                        .attribute(OAuth2Authorization.AUTHORIZED_SCOPE_ATTRIBUTE_NAME, scopes)
                        .attribute(Principal.class.getName(), usernamePasswordAuthentication);
            }
            else {
                authorizationBuilder.id(accessToken.getTokenValue()).accessToken(accessToken);
            }

            // ----- Refresh token -----
            OAuth2RefreshToken refreshToken = null;
            if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
                    // Do not issue refresh token to public client
                    !oAuth2ClientAuthentication.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {

                if (this.refreshTokenGenerator != null) {
                    Instant issuedAt = Instant.now();
                    Instant expiresAt = issuedAt.plus(registeredClient.getTokenSettings().getRefreshTokenTimeToLive());
                    refreshToken = new OAuth2RefreshToken(this.refreshTokenGenerator.get(), issuedAt, expiresAt);
                }
                else {
                    tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
                    OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
                    if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                        OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                                "The token generator failed to generate the refresh token.", ERROR_URI);
                        throw new OAuth2AuthenticationException(error);
                    }
                    refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
                }
                authorizationBuilder.refreshToken(refreshToken);
            }

            OAuth2Authorization authorization = authorizationBuilder.build();

            this.authorizationService.save(authorization);

            log.debug("returning OAuth2AccessTokenAuthenticationToken");

            return new OAuth2AccessTokenAuthenticationToken(registeredClient, oAuth2ClientAuthentication, accessToken,
                    refreshToken, Objects.requireNonNull(authorization.getAccessToken().getClaims()));

        }catch (Exception ex) {
            log.error("the exception maybe in authenticate:  ", ex);
            throw throwOAuth2AuthenticationException(authentication, (AuthenticationException) ex);
        }

    }



    /**
     * Description: 匹配抛出异常
     * @author wxl
     * Date: 2022/10/24 16:06
     * @param authentication the authentication
     * @param ex exception
     * @return org.springframework.security.core.AuthenticationException
     */
    private AuthenticationException throwOAuth2AuthenticationException(Authentication authentication,
                                                                       AuthenticationException ex) {
        return switch (ex){
            case UsernameNotFoundException ignored -> new OAuth2AuthenticationException(
                    new OAuth2Error(OAuth2ErrorConstant.USERNAME_NOT_FOUND,
                        "username not found",
                         ""));

            case BadCredentialsException ignored -> new OAuth2AuthenticationException(
                    new OAuth2Error(OAuth2ErrorConstant.BAD_CREDENTIALS,
                            "Bad credentials",
                            ""));
            case LockedException ignored -> new OAuth2AuthenticationException(
                    new OAuth2Error(OAuth2ErrorConstant.USER_LOCKED,
                             "User account is locked",
                            ""));
            case DisabledException ignored -> new OAuth2AuthenticationException(
                    new OAuth2Error(OAuth2ErrorConstant.USER_DISABLE,
                    "User is disabled",
                    ""));
            case AccountExpiredException ignored -> new OAuth2AuthenticationException(
                    new OAuth2Error(OAuth2ErrorConstant.USER_EXPIRED,
                            "User account has expired",
                            ""));
            case CredentialsExpiredException ignored -> new OAuth2AuthenticationException(
                    new OAuth2Error(OAuth2ErrorConstant.CREDENTIALS_EXPIRED,
                            "User credentials have expired",
                            ""));
            case OAuth2ScopeException ignored -> new OAuth2AuthenticationException(
                    new OAuth2Error(OAuth2ErrorCodes.INVALID_SCOPE,
                            "invalid_scope",
                            ""));
            default -> new OAuth2AuthenticationException(OAuth2ErrorConstant.UN_KNOW_LOGIN_ERROR);
        };
    }




    /**
     * Description: 获取OAuth2ClientAuthenticationToken
     * @author wxl
     * Date: 2022/10/24 16:06
     * @param authentication the authenticated
     * @return org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken
     */
    private OAuth2ClientAuthenticationToken getOAuth2ClientAuthentication
            (Authentication authentication) {

        OAuth2ClientAuthenticationToken clientAuthenticationToken = null;
        Object principal = authentication.getPrincipal();

        //判断传入客户端是否实现或者与本类相同
        if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(principal.getClass())){
            clientAuthenticationToken = (OAuth2ClientAuthenticationToken)principal;
        }

        if (clientAuthenticationToken != null && clientAuthenticationToken.isAuthenticated()) {
            return clientAuthenticationToken;
        }


        throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
    }

}
