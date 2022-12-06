package com.obeast.auth.support.password;

import com.obeast.auth.support.base.OAuth2BaseAuthenticationProvider;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author wxl
 * Date 2022/12/6 10:47
 * @version 1.0
 * Description: password provider
 */
@Slf4j
public class OAuth2PasswordAuthenticationProvider extends OAuth2BaseAuthenticationProvider<OAuth2PasswordAuthenticationToken> {

    @Setter
    private UserDetailsService userDetailsService;

    private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Setter
    private PasswordEncoder passwordEncoder;


    public OAuth2PasswordAuthenticationProvider(
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        super(authorizationService, tokenGenerator);
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UsernamePasswordAuthenticationToken buildAndAuthenticateUsernamePasswordToken(Map<String, Object> reqParams) {
        String username = (String) reqParams.get(OAuth2ParameterNames.USERNAME);
        String password = (String) reqParams.get(OAuth2ParameterNames.PASSWORD);
        if (!StringUtils.hasText(password) || !StringUtils.hasText(username)) {
            log.error("密码或者用户名不能为空");
            throw new BadCredentialsException("username or password are must be not null");
        }
        UserDetails user = this.verifyUserDetails(username, password);
//        todo 限制一个用户登录多端最大个数
        return new UsernamePasswordAuthenticationToken(username, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean supports = OAuth2PasswordAuthenticationToken.class.isAssignableFrom(authentication);
        log.debug("supports authentication=" + authentication + " returning " + supports);
        return supports;
    }



    @Override
    public void supportClient(RegisteredClient registeredClient) {
        assert registeredClient != null;
        if (!registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.PASSWORD)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }
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




}
