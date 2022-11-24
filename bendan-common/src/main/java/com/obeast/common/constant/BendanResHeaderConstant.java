package com.obeast.common.constant;


/**
 * @author wxl
 * Date 2022/11/3 9:23
 * @version 1.0
 * Description: 请求头常量
 */
public interface BendanResHeaderConstant {

    /**
     * token前缀
     * */
    String bearer = "Bearer";

    /**
     * 网关请求头Key
     * */
    String from = "from";

    /**
     * 认证头名字
     * */
    String authorization =  "Authorization";

    /**
     * 网关请求头Value gateway
     * */
    String bendanValue = "bendan";


    /**
     * 网关请求头Value gateway
     * */
    String bendanValueEncode = "YmVuZGFu";


}

