package com.obeast.security.annotation;


import com.obeast.security.config.ResourceConfig;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.lang.annotation.*;

/**
 * @author wxl
 * Date 2022/12/3 23:47
 * @version 1.0
 * Description:
 */
@Documented
@Inherited
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ResourceConfig.class, ResourceConfig.class})
public @interface EnableBendanResourceServer {
}
