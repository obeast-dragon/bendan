package com.obeast.security.resource;

import com.obeast.core.constant.UserLoginConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wxl
 * Date 2022/11/28 16:59
 * @version 1.0
 * Description: BearerToken解析配置
 */
@Slf4j
@RequiredArgsConstructor
public class BendanBearerTokenExtractor implements BearerTokenResolver {

    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-:._~+/]+=*)$",
            Pattern.CASE_INSENSITIVE);

    private final PathMatcher pathMatcher = new AntPathMatcher();
    private final ResourcesProperties urlProperties;

    @Override
    public String resolve(HttpServletRequest request) {
        boolean match = urlProperties
                .getUrls()
                .stream()
                .anyMatch(url -> pathMatcher.match(url, request.getRequestURI()));
        if (match) {
            return null;
        }
        return resolveFromAuthorizationHeader(request);
    }

    /**
     * Description: 解析请求头中的token
     * @author wxl
     * Date: 2022/11/28 17:57
     * @param request request
     * @return java.lang.String
     */
    private String resolveFromAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null){
            throw new InvalidBearerTokenException(null);
        }
        Matcher matcher = authorizationPattern.matcher(authorization);
        if (!matcher.matches()) {
            BearerTokenError error = BearerTokenErrors.invalidToken("Bearer token is malformed");
            throw new OAuth2AuthenticationException(error);
        }
        return matcher.group(UserLoginConstant.AUTHORIZATION);
    }


}
