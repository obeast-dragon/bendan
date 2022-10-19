package com.obeast.common.auto;

import com.obeast.common.config.FieldFillConfig;
import com.obeast.common.config.MyBatisPlusConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;


/**
 * @author wxl
 * Date 2022/10/19 16:51
 * @version 1.0
 * Description: 共有类的自动配置
 */
@AutoConfiguration
public class CommonAutoConfiguration {


    /**
     * 字段插入
     * */
    @Bean
    @ConditionalOnMissingBean(FieldFillConfig.class)
    public FieldFillConfig fieldFillConfig() {
        return new FieldFillConfig();
    }

    /**
     * mybatis-plus 分页
     * */
    @Bean
    @ConditionalOnMissingBean(MyBatisPlusConfig.class)
    public MyBatisPlusConfig yBatisPlusConfig() {
        return new MyBatisPlusConfig();
    }



}
