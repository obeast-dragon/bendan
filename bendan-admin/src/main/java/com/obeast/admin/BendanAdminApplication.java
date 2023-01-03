package com.obeast.admin;


import com.obeast.security.annotation.EnableBendanResourceServer;
import com.obeast.security.business.service.remote.OAuth2TokenEndpoint;
import com.obeast.swagger.annotation.EnableBendanSwagger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author wxl
 * Date 2022/10/4 0:04
 * @version 1.0
 * Description:
 */
@EnableFeignClients(basePackageClasses = OAuth2TokenEndpoint.class)
@EnableBendanSwagger
@EnableDiscoveryClient
@EnableBendanResourceServer
@MapperScan("com.obeast.security.business.dao")
@SpringBootApplication
public class BendanAdminApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(BendanAdminApplication.class, args);
//        String[] beanDefinitionNames = run.getBeanDefinitionNames();
//        for (String beanDefinitionName : beanDefinitionNames) {
//            System.out.println(beanDefinitionName);
//        }
    }
}
