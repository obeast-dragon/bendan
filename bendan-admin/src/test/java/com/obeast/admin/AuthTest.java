package com.obeast.admin;

import com.obeast.security.business.service.remote.OAuth2TokenEndpoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wxl
 * Date 2022/12/7 21:22
 * @version 1.0
 * Description:
 */
@SpringBootTest
public class AuthTest {

    @Autowired
    private OAuth2TokenEndpoint oAuth2TokenEndpoint;

    @Test
    public void test () {

    }
}
