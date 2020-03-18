package com.wmding.springcloud.controller;

import com.wmding.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 明月
 * @version 1.0
 * @date 2020-03-16 09:26
 * @description:
 */
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentOk(@PathVariable("id") Integer id) {
        String s = paymentService.paymentInfo_ok(id);
        log.error(s);
        return s;
    }


    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentTimeOut(@PathVariable("id") Integer id) {
        String s = paymentService.paymentInfo_TimeOut(id);
        log.error(s);
        return s;
    }
}
