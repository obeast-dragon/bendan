package com.obeast.auth.support.resourceServer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wxl
 * Date 2022/11/28 16:59
 * @version 1.0
 * Description: BearerToken解析配置
 */
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
                .anyMatch(
                        url -> pathMatcher.match(url, request.getRequestURI())
                );
        if (match) {
            return null;
        }
        final String authorizationHeader =  resolveFromAuthorizationHeader(request);
        if (authorizationHeader != null) {
            return authorizationHeader;
        }
        return null;
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

        if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
            return null;
        }
        return null;
    }

}
