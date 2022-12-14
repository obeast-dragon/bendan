package com.obeast.auth;


import com.obeast.security.business.service.remote.OAuth2TokenEndpoint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@EnableFeignClients(basePackageClasses = OAuth2TokenEndpoint.class)
@MapperScan("com.obeast.security.business.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class BendanOAuthApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(BendanOAuthApplication.class, args);
//        String[] beanDefinitionNames = run.getBeanDefinitionNames();
//        for (String beanDefinitionName : beanDefinitionNames) {
//            System.out.println(beanDefinitionName);
//        }

    }

}
