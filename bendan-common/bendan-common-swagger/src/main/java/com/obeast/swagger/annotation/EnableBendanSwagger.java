package com.obeast.swagger.annotation;


import com.obeast.swagger.config.SwaggerAutoConfig;
import com.obeast.swagger.config.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author wxl
 * Date 2022/12/6 21:04
 * @version 1.0
 * Description: 开启swagger
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerAutoConfig.class, SwaggerProperties.class})
public @interface EnableBendanSwagger {
}
