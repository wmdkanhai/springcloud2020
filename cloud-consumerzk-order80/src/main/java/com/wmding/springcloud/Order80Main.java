package com.wmding.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 明月
 * @version 1.0
 * @date 2020-03-14 22:37
 * @description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Order80Main {

    public static void main(String[] args) {
        SpringApplication.run(Order80Main.class, args);
    }
}
