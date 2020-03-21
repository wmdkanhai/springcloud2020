package com.wmding.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 明月
 * @version 1.0
 * @date 2020-03-21 23:02
 * @description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Config3377Main {
    public static void main(String[] args) {
        SpringApplication.run(Config3377Main.class, args);
    }
}
