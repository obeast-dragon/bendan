package com.obeast.auth.support.resource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author wxl
 * Date 2022/11/28 17:41
 * @version 1.0
 * Description: 资源服务器配置
 */
@ConfigurationProperties(prefix = "security.resources.ignore")
public class ResourcesProperties {

    @Getter
    @Setter
    private List<String> urls;
}
