package com.obeast.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BendanGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(BendanGatewayApplication.class, args);
    }

}
