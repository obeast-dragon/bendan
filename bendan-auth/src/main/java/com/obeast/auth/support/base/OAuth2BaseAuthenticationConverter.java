package com.obeast.auth.support.base;

import com.obeast.auth.utils.OAuth2Utils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wxl
 * Date 2022/10/24 11:42
 * @version 1.0
 * Description: 自定义baseConverter
 */
public abstract class OAuth2BaseAuthenticationConverter<T extends OAuth2BaseAuthenticationToken>
        implements AuthenticationConverter {

    /**
     * Description: 判断是否grantType
     * @author wxl
     * Date: 2022/10/24 11:49
     * @param grantType the type of grant
     * @return boolean
     */
    public abstract boolean support(String grantType);


    /**
     * Description: 校验参数
     * @author wxl
     * Date: 2022/10/24 17:49
     * @param parameters the parameters
     */
    public abstract void checkRequestParams(MultiValueMap<String, String> parameters );

    /**
     * Description: 构建具体类型的token
     * @author wxl
     * Date: 2022/10/24 17:54
     * @param clientPrincipal the clientPrincipal
     * @param requestedScopes the scope
     * @param additionalParameters the params
     * @return T
     */
    public abstract T buildConvertToken(Authentication clientPrincipal, Set<String> requestedScopes,
                                        Map<String, Object> additionalParameters);

    @Override
    public Authentication convert(HttpServletRequest request) {

        // grant_type (REQUIRED)
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!support(grantType)) {
            return null;
        }

        MultiValueMap<String, String> parameters = OAuth2Utils.getParameters(request);
        // scope (OPTIONAL)
        String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);
        if (StringUtils.hasText(scope) && parameters.get(OAuth2ParameterNames.SCOPE).size() != 1) {
            OAuth2Utils.throwNullError(
                    OAuth2ErrorCodes.INVALID_REQUEST,
                    OAuth2ParameterNames.SCOPE,
                    OAuth2Utils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        Set<String> requestedScopes = null;
        if (StringUtils.hasText(scope)) {
            requestedScopes = new HashSet<>(Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
        }

        // 校验个性化参数
        checkRequestParams(parameters);


        // 获取当前已经认证的客户端信息
        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
        if (clientPrincipal == null) {
            OAuth2Utils.throwNullError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ErrorCodes.INVALID_CLIENT,
                    OAuth2Utils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        // 扩展信息
        Map<String, Object> additionalParameters = parameters.entrySet().stream()
                .filter(e -> !e.getKey().equals(OAuth2ParameterNames.GRANT_TYPE)
                        && !e.getKey().equals(OAuth2ParameterNames.SCOPE))
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));

        // 创建token
        return buildConvertToken(clientPrincipal, requestedScopes, additionalParameters);
    }
}
