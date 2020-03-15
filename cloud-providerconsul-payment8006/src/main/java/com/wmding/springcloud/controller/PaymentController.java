package com.wmding.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.diff.myers.MyersDiff;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author 明月
 * @version 1.0
 * @date 2020-03-15 11:07
 * @description:
 */

@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "/payment/consul")
    public String paymentConsul(){
        String msg = "SpringCloud with consul " + serverPort + "\t" + UUID.randomUUID().toString();
        log.info(msg);
        return msg;
    }

}
