package com.obeast.auth.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author wxl
 * Date 2022/10/31 15:50
 * @version 1.0
 * Description: OAuth2Authorization redis缓存实现
 */
@Service
@RequiredArgsConstructor
public class BendanOAuth2AuthorizationServiceImpl implements OAuth2AuthorizationService {

    @Value("${redis.token.expiration}")
    private Long TIMEOUT;

    private static final String AUTHORIZATION = "token";

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "OAuth2Authorization cannot be null");

        if (isStateNonNull(authorization)) {
            String state = authorization.getAttribute("state");
            redisTemplate.opsForValue().set(
                    createRedisKey(OAuth2ParameterNames.STATE, state),
                    authorization,
                    TIMEOUT,
                    TimeUnit.MINUTES
            );
        }
        if (isCodeNonNull(authorization)) {
            OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization
                    .getToken(OAuth2AuthorizationCode.class);
            OAuth2AuthorizationCode authorizationCodeToken = authorizationCode.getToken();
            long between = ChronoUnit.MINUTES.between(authorizationCodeToken.getIssuedAt(),
                    authorizationCodeToken.getExpiresAt());
            redisTemplate.opsForValue().set(
                    createRedisKey(OAuth2ParameterNames.CODE, authorizationCodeToken.getTokenValue()),
                    authorization,
                    between,
                    TimeUnit.MINUTES
            );

        }
        if (isRefreshTokenNonNull(authorization)) {
            OAuth2RefreshToken refreshToken = authorization.getRefreshToken().getToken();
            long between = ChronoUnit.SECONDS.between(refreshToken.getIssuedAt(), refreshToken.getExpiresAt());
            redisTemplate.opsForValue().set(
                    createRedisKey(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken.getTokenValue()),
                    authorization,
                    between,
                    TimeUnit.SECONDS
            );
        }

        if (isAccessTokenNonNull(authorization)) {
            OAuth2AccessToken accessToken = authorization.getAccessToken().getToken();
            long between = ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt());
            redisTemplate.opsForValue().set(
                    createRedisKey(OAuth2ParameterNames.ACCESS_TOKEN, accessToken.getTokenValue()),
                    authorization,
                    between,
                    TimeUnit.SECONDS
            );
        }


    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");

        List<String> keys =  new ArrayList<>();
        if (isStateNonNull(authorization)) {
            String state = authorization.getAttribute("state");
            keys.add(createRedisKey(OAuth2ParameterNames.STATE, state));

        }
        if (isCodeNonNull(authorization)) {
            OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization
                    .getToken(OAuth2AuthorizationCode.class);
            OAuth2AuthorizationCode authorizationCodeToken = authorizationCode.getToken();
            keys.add(createRedisKey(OAuth2ParameterNames.CODE, authorizationCodeToken.getTokenValue()));


        }
        if (isRefreshTokenNonNull(authorization)) {
            OAuth2RefreshToken refreshToken = authorization.getRefreshToken().getToken();
            keys.add(createRedisKey(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken.getTokenValue()));
        }

        if (isAccessTokenNonNull(authorization)) {
            OAuth2AccessToken accessToken = authorization.getAccessToken().getToken();
            keys.add(createRedisKey(OAuth2ParameterNames.ACCESS_TOKEN, accessToken.getTokenValue()));
        }
        redisTemplate.delete(keys);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");
        Assert.notNull(tokenType, "tokenType cannot be empty");
        redisTemplate.setValueSerializer(RedisSerializer.java());
        return (OAuth2Authorization) redisTemplate.opsForValue().get(createRedisKey(tokenType.getValue(), token));
    }


    /**
     * Description: 创建redis存储的key
     *
     * @param type  token type
     * @param value token value
     * @return java.lang.String
     * @author wxl
     * Date: 2022/10/31 17:02
     */
    private String createRedisKey(String type, String value) {
        return String.format("%s::%s::%s", AUTHORIZATION, type, value);
    }

    /**
     * Description: 判断state是否为空
     *
     * @param authorization OAuth2Authorization
     * @return boolean
     * @author wxl
     * Date: 2022/10/31 17:00
     */
    private static boolean isStateNonNull(OAuth2Authorization authorization) {
        return Objects.nonNull(authorization.getAttribute("state"));
    }

    /**
     * Description: 判断授权码是否为空
     *
     * @param authorization OAuth2Authorization
     * @return boolean
     * @author wxl
     * Date: 2022/10/31 17:00
     */
    private static boolean isCodeNonNull(OAuth2Authorization authorization) {
        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization
                .getToken(OAuth2AuthorizationCode.class);
        return Objects.nonNull(authorizationCode);
    }

    /**
     * Description: 判断refreshToken是否为控
     *
     * @param authorization OAuth2Authorization
     * @return boolean
     * @author wxl
     * Date: 2022/10/31 17:01
     */
    private static boolean isRefreshTokenNonNull(OAuth2Authorization authorization) {
        return Objects.nonNull(authorization.getRefreshToken());
    }

    /**
     * Description: 判断AccessToken是否为空
     *
     * @param authorization OAuth2Authorization
     * @return boolean
     * @author wxl
     * Date: 2022/10/31 17:02
     */
    private static boolean isAccessTokenNonNull(OAuth2Authorization authorization) {
        return Objects.nonNull(authorization.getAccessToken());
    }
}
