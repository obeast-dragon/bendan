package com.obeast.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.obeast.bendan.dao")
@SpringBootApplication
public class BendanApplication {

    public static void main(String[] args) {
        SpringApplication.run(BendanApplication.class, args);
    }

}

