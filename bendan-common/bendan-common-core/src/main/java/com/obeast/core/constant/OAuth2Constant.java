package com.obeast.core.constant;

/**
 * @author wxl
 * Date 2022/10/20 11:31
 * @version 1.0
 * Description: 客户端常量
 */
public interface OAuth2Constant {

    /**
     * access_token
     * */
    String ACCESS_TOKEN = "access_token";

    /**
     * refresh_token
     * */
    String REFRESH_TOKEN = "refresh_token";

    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * redis 存储 token 名字
     * */
    String TOKEN = "token";

    /**
     *  AUTHORIZATION header
     * */
    String AUTHORIZATION = "Authorization";

    /**
     * userinfo
     * */
    String USERINFO = "userinfo";

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

    enum Scope{
        ALL("all"),
        READ("message.read"),
        WRITE("message.write")
        ;
        private final String name;

        Scope(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }


    /**
     * Description: 创建redis存储的key
     *
     * @param type  token type
     * @param value token value
     * @return java.lang.String
     * @author wxl
     * Date: 2022/10/31 17:02
     */
    static String createRedisKey(String type, String value) {
        return String.format("%s::%s::%s", TOKEN, type, value);
    }
}
