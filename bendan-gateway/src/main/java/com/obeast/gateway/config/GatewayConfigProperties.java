package com.obeast.gateway.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

/**
 * @author wxl
 * Date 2022/11/24 17:40
 * @version 1.0
 * Description: 网关配置文件
 */
@Data
@RefreshScope
@ConfigurationProperties("gateway")
public class GatewayConfigProperties {

	/**
	 * 网关不需要校验验证码的客户端
	 */
	private List<String> ignoreClients;


	/**
	 * 不需要拦截的url
	 * */
	private List<String> ignoreUrls;

}
