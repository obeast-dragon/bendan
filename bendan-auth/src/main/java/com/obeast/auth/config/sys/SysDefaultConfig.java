package com.obeast.auth.config.sys;

import com.obeast.common.config.FieldFillConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wxl
 * Date 2022/10/19 15:49
 * @version 1.0
 * Description: 系统默认配置
 */
@Configuration
public class SysDefaultConfig {

    @Bean
    FieldFillConfig createFieldFillConfig(){
        return new FieldFillConfig();
    }
}
