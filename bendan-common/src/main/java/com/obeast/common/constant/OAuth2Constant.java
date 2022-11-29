package com.obeast.common.constant;

/**
 * @author wxl
 * Date 2022/10/20 11:31
 * @version 1.0
 * Description: 客户端常量
 */
public interface OAuth2Constant {


    /**
     * token 名
     * */
    String authorization = "token";

    /**
     * 手机号登录
     */
    String APP = "app";

    /**
     * 默认登录URL
     */
    String LOGIN_URL = "/userinfo/login";


    enum Type {
        MIN_APP("min-app"),
        WEB_BROWSER("web-browser"),
        ;
        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    enum RequestTokenParam {
        CLIENT_ID("client-id"),
        CLIENT_SECRET("client-secret"),
        GRANT_TYPE("grant-type"),
        USERNAME("username"),
        PASSWORD("password"),
        ;
        private final String name;
        RequestTokenParam(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    enum GrantType {
        AUTHORIZATION_CODE("authorization_code"),
        /** @deprecated */
        @Deprecated
        IMPLICIT("implicit"),
        REFRESH_TOKEN("refresh_token"),
        CLIENT_CREDENTIALS("client_credentials"),
        PASSWORD("password"),
        WT_BEARER("urn:ietf:params:oauth:grant-type:jwt-bearer")
        ;
        private final String name;


        GrantType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
