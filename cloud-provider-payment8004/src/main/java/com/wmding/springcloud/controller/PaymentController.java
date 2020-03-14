package com.wmding.springcloud.controller;

import cn.hutool.core.lang.UUID;;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author 明月
 * @version 1.0
 * @date 2020-03-08 17:49
 * @description:
 */
@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "payment/zk")

    public String paymentZk() {
        return "SpringCloud with zookeeper:" + serverPort + " \t " + UUID.randomUUID().toString();
    }
}
