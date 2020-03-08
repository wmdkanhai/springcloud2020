package com.wmding.springcloud.controller;

import com.wmding.springcloud.entities.CommonResult;
import com.wmding.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author 明月
 * @version 1.0
 * @date 2020-03-08 19:36
 * @description:
 */

@RestController
@Slf4j
public class OrderContrlooer {

    //public static final String PAYMENT_URL = "http://localhost:8001";
    /**
     * 通过在eureka上注册过的微服务名称调用
     */
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult getPayment(@PathVariable("id") Long id){
        CommonResult forObject = restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        return forObject;
    }


    @GetMapping("/consumer/payment/create")
    public CommonResult getPayment(Payment payment){
        CommonResult forObject = restTemplate.postForObject(PAYMENT_URL + "/payment/create/",payment, CommonResult.class);
        return forObject;
    }

}
