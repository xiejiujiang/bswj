package com.example.bswj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.example.bswj.mapper")
public class BswjApplication {

    public static void main(String[] args) {
        SpringApplication.run(BswjApplication.class, args);
    }

}