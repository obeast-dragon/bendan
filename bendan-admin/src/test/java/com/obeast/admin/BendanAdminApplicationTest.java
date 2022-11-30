package com.obeast.admin;



import com.obeast.admin.business.dao.BendanSysRoleDao;
import com.obeast.admin.business.service.remote.OAuth2TokenRemote;
import com.obeast.business.vo.OAuth2TokenParams;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

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
    OAuth2TokenRemote oAuth2TokenRemote;

    @Autowired
    BendanSysRoleDao bendanSysRoleDao;

    @Autowired
    private CacheManager cacheManager;
    @Test
    void testSaveUser() {
        Cache cache = cacheManager.getCache("user");
        cache.put("user", "12121");
        Cache.ValueWrapper user = cache.get("user");
        Object o = user.get();
        System.out.println(o);
    }

    @Test
    void login() {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", "messaging-client" );
        params.put("client_secret","secret");
        params.put("grant_type","password");
        params.put("username","user");
        params.put("password","password");
        OAuth2TokenParams oAuth2Params = new OAuth2TokenParams();
        oAuth2Params.setClient_id("messaging-client");
        oAuth2Params.setClient_secret("secret");
        oAuth2Params.setGrant_type("password");
        oAuth2Params.setUsername("user");
        oAuth2Params.setPassword("password");


        System.out.println(oAuth2TokenRemote.getAccessToken(oAuth2Params));
//        userInfoService.login("user", "password");
    }

}
