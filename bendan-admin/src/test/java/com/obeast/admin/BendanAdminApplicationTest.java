package com.obeast.admin;


import cn.hutool.crypto.digest.BCrypt;
import com.obeast.admin.business.vo.OAuth2Params;
import com.obeast.admin.business.entity.UserInfoEntity;
import com.obeast.admin.business.service.UserInfoService;
import com.obeast.admin.business.service.remote.OAuth2Remote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxl
 * Date 2022/10/4 0:04
 * @version 1.0
 * Description:
 */
@SpringBootTest
public class BendanAdminApplicationTest {


    @Autowired
    UserInfoService userInfoService;

    @Autowired
    OAuth2Remote oAuth2Remote;

    @Test
    void testSaveUser() {
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setUsername("user");
        userInfo.setPassword("{bcrypt}"+BCrypt.hashpw("password"));
        userInfoService.add(userInfo);
    }

    @Test
    void login() {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", "messaging-client" );
        params.put("client_secret","secret");
        params.put("grant_type","password");
        params.put("username","user");
        params.put("password","password");
        OAuth2Params oAuth2Params = new OAuth2Params();
        oAuth2Params.setClient_id("messaging-client");
        oAuth2Params.setClient_secret("secret");
        oAuth2Params.setGrant_type("password");
        oAuth2Params.setUsername("user");
        oAuth2Params.setPassword("password");


        System.out.println(oAuth2Remote.getAccessToken(oAuth2Params));
//        userInfoService.login("user", "password");
    }

}
