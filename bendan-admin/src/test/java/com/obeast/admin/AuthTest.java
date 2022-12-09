package com.obeast.admin;

import com.obeast.business.vo.OAuth2TokenParams;
import com.obeast.core.constant.OauthScopeConstant;
import com.obeast.security.business.service.remote.OAuth2TokenRemote;
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
    private OAuth2TokenRemote oAuth2TokenRemote;

    @Test
    public void test () {
        String header = "Basic d2ViOmJlbmRhbg==";
        OAuth2TokenParams oAuth2Params = new OAuth2TokenParams();
        oAuth2Params.setGrant_type("password");
        oAuth2Params.setUsername("admin");
        oAuth2Params.setPassword("password");
        oAuth2Params.setScope(OauthScopeConstant.ALL);
        System.out.println(oAuth2TokenRemote.getAccessToken(header, oAuth2Params));

    }
}
