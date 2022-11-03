package com.obeast.common.constant;


/**
 * @author wxl
 * Date 2022/11/3 9:23
 * @version 1.0
 * Description: 请求头常量
 */
public interface RequestHeaderConstant {


    /**
     * 网关请求头Key
     * */
    String gatewayKey = "IdCard";

    /**
     * feign请求头Key
     * */
    String openFeignKey = "from";


    /**
     * 网关请求头Value gateway
     * */
    String gatewayValue = "gateway";

    /**
     * feign请求头Value openFeign
     * */
    String openFeignValue = "openFeign";

}

