package com.obeast.auth.support.handler.result;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author wxl
 * Date 2022/10/31 10:55
 * @version 1.0
 * Description: 自定义AccessTokenResponse返回结果
 */public final class OAuth2CustomizeAccessTokenResponse {
    private OAuth2AccessToken accessToken;
    private OAuth2RefreshToken refreshToken;

//    private OAuth2AccessToken idToken;
    private Map<String, Object> additionalParameters;

    private OAuth2CustomizeAccessTokenResponse() {
    }

    public OAuth2AccessToken getAccessToken() {
        return this.accessToken;
    }

//    @Nullable
//    public OAuth2AccessToken getIdToken() {
//        return idToken;
//    }

    @Nullable
    public OAuth2RefreshToken getRefreshToken() {
        return this.refreshToken;
    }

    public Map<String, Object> getAdditionalParameters() {
        return this.additionalParameters;
    }

    public static Builder withToken(String tokenValue) {
        return new Builder(tokenValue);
    }

    public static Builder withResponse(OAuth2CustomizeAccessTokenResponse response) {
        return new Builder(response);
    }

    public static final class Builder {
        private String tokenValue;
        private OAuth2AccessToken.TokenType tokenType;
        private Instant issuedAt;
        private Instant expiresAt;
        private long expiresIn;
        private Set<String> scopes;
        private String refreshToken;
        private String idToken;
        private Map<String, Object> additionalParameters;

        private Builder(OAuth2CustomizeAccessTokenResponse response) {
            OAuth2AccessToken accessToken = response.getAccessToken();
            this.tokenValue = accessToken.getTokenValue();
            this.tokenType = accessToken.getTokenType();
            this.issuedAt = accessToken.getIssuedAt();
            this.expiresAt = accessToken.getExpiresAt();
            this.scopes = accessToken.getScopes();
            this.refreshToken = response.getRefreshToken()
                    != null ? response.getRefreshToken().getTokenValue() : null;
//            this.idToken = response.getIdToken()
//                    != null ? response.getIdToken().getTokenValue() : null;
            this.additionalParameters = response.getAdditionalParameters();
        }

        private Builder(String tokenValue) {
            this.tokenValue = tokenValue;
        }

        public Builder tokenType(OAuth2AccessToken.TokenType tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public Builder expiresIn(long expiresIn) {
            this.expiresIn = expiresIn;
            this.expiresAt = null;
            return this;
        }

        public Builder scopes(Set<String> scopes) {
            this.scopes = scopes;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder idToken(String idToken) {
            this.idToken = idToken;
            return this;
        }

        public Builder additionalParameters(Map<String, Object> additionalParameters) {
            this.additionalParameters = additionalParameters;
            return this;
        }

        public OAuth2CustomizeAccessTokenResponse build() {
            Instant issuedAt = this.getIssuedAt();
            Instant expiresAt = this.getExpiresAt();
            OAuth2CustomizeAccessTokenResponse accessTokenResponse = new OAuth2CustomizeAccessTokenResponse();
            accessTokenResponse.accessToken =
                    new OAuth2AccessToken(this.tokenType, this.tokenValue, issuedAt, expiresAt, this.scopes);
            if (StringUtils.hasText(this.refreshToken)) {
                accessTokenResponse.refreshToken = new OAuth2RefreshToken(this.refreshToken, issuedAt);
            }

//            if (StringUtils.hasText(this.idToken)) {
//                accessTokenResponse.idToken =  new OAuth2AccessToken(this.tokenType, this.idToken, issuedAt, expiresAt, this.scopes);
//            }
            accessTokenResponse.additionalParameters = Collections.unmodifiableMap(CollectionUtils.isEmpty(this.additionalParameters) ? Collections.emptyMap() : this.additionalParameters);
            return accessTokenResponse;
        }

        private Instant getIssuedAt() {
            if (this.issuedAt == null) {
                this.issuedAt = Instant.now();
            }

            return this.issuedAt;
        }

        private Instant getExpiresAt() {
            if (this.expiresAt == null) {
                Instant issuedAt = this.getIssuedAt();
                this.expiresAt = this.expiresIn > 0L ? issuedAt.plusSeconds(this.expiresIn) : issuedAt.plusSeconds(1L);
            }

            return this.expiresAt;
        }
    }
}