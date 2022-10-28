package com.obeast.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wxl
 * Date 2022/10/4 0:04
 * @version 1.0
 * Description:
 */
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.obeast.admin.business.dao")
@SpringBootApplication
public class BendanAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BendanAdminApplication.class, args);
    }
}
