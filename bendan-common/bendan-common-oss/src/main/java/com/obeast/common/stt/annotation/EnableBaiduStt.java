package com.obeast.common.stt.annotation;

import com.obeast.common.stt.config.BaiduSttAutoConfig;
import com.obeast.common.stt.properties.ThirdPartyBaiduSttProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author wxl
 * Date 2023/1/4 12:45
 * @version 1.0
 * Description: baidu
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ThirdPartyBaiduSttProperties.class, BaiduSttAutoConfig.class})
public @interface EnableBaiduStt {
}
