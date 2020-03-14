package com.wmding.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author 明月
 * @version 1.0
 * @date 2020-03-14 22:43
 * @description:
 */
@RestController
public class OrderZkController {

    public static final String INVOKE_URL = "http://cloud-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    /**
     * http://localhost/consumer/payment/zk
     */
    @GetMapping("/consumer/payment/zk")
    public String paymentInfo() {
        return restTemplate.getForObject(INVOKE_URL + "/payment/zk", String.class);
    }

}
