package com.obeast.core.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;


/**
 * @author wxl
 * Date 2022/11/1 12:10
 * @version 1.0
 * Description: mybatis-plus 配置
 */
@AutoConfiguration
@ConditionalOnBean(MybatisPlusAutoConfiguration.class)
public class MyBatisPlusConfig {

    /**
     * 字段插入
     * */
    @Bean
    public FieldFillConfig fieldFillConfig() {
        return new FieldFillConfig();
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
