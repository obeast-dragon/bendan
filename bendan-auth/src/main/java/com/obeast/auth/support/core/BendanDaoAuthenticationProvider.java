package com.obeast.auth.support.core;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;

/**
 * @author wxl
 * Date 2022/10/26 17:34
 * @version 1.0
 * Description: DaoAuthenticationProvider自定义
 */
public class BendanDaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {


    /**
     * The plaintext password used to perform PasswordEncoder#matches(CharSequence,
     * String)} on when the user is not found to avoid SEC-2056.
     */
    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

    private final static BasicAuthenticationConverter basicConvert = new BasicAuthenticationConverter();

    private PasswordEncoder passwordEncoder;


    /**
     * The password used to perform {@link PasswordEncoder#matches(CharSequence, String)}
     * on when the user is not found to avoid SEC-2056. This is necessary, because some
     * {@link PasswordEncoder} implementations will short circuit if the password is not
     * in a valid format.
     */
    private volatile String userNotFoundEncodedPassword;

    private UserDetailsService userDetailsService;

    private UserDetailsPasswordService userDetailsPasswordService;


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        return null;
    }
}
