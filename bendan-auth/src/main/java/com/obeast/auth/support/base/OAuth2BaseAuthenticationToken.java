package com.obeast.auth.support.base;

import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author wxl
 * Date 2022/10/24 11:45
 * @version 1.0
 * Description: 自定义认证Token (在AbstractAuthenticationToken上的扩展的抽象类)
 */
public abstract class OAuth2BaseAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * the authorization grant type.
     * */
    @Getter
    private final AuthorizationGrantType authorizationGrantType;

    @Getter
    private final Authentication clientPrincipal;

    /**
     * the additional parameters.
     * */
    @Getter
    private final Map<String, Object> additionalParameters;

    @Getter
    private final Set<String> scopes;

    /**
     * Sub-class constructor.
     *
     * @param authorizationGrantType the authorization grant type
     * @param clientPrincipal the authenticated client principal
     * @param additionalParameters the additional parameters
     */
    public OAuth2BaseAuthenticationToken(
            AuthorizationGrantType authorizationGrantType,
            Authentication clientPrincipal,
            @Nullable Set<String> scopes,
            @Nullable Map<String, Object> additionalParameters
    ) {
        super(Collections.emptyList());
        Assert.notNull(authorizationGrantType, "authorizationGrantType cannot be null");
        Assert.notNull(clientPrincipal, "clientPrincipal cannot be null");
        this.authorizationGrantType = authorizationGrantType;
        this.clientPrincipal = clientPrincipal;
        this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
        this.additionalParameters = Collections.unmodifiableMap(
                additionalParameters != null ? new HashMap<>(additionalParameters) : Collections.emptyMap());
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    /**
     * 获取用户名
     */
    @Override
    public Object getPrincipal() {
        return this.clientPrincipal;
    }


}
