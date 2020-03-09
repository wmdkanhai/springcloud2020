package com.wmding.springcloud.controller;

import com.wmding.springcloud.entities.CommonResult;
import com.wmding.springcloud.entities.Payment;
import com.wmding.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
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
    private String port;

    @Autowired
    private PaymentService paymentService;


    @PostMapping("payment/create")
    public CommonResult paymentCreate(@RequestBody Payment payment) {

        if (StringUtils.isEmpty(payment.getSerial())) {
            return new CommonResult(500, "参数为空");
        }
        int i = paymentService.create(payment);
        log.info("paymentCreate->{}，端口号->{}", i, port);

        if (i > 0) {
            return new CommonResult(200, "插入数据库成功，端口号：" + port, i);
        } else {
            return new CommonResult(444, "插入数据库失败");
        }

    }


    @GetMapping("payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {

        Payment payment = paymentService.getPayment(id);

        log.info("getPaymentById->{}，端口号->{}", payment, port);

        if (payment != null) {
            return new CommonResult(200, "查询成功，端口号：" + port, payment);
        } else {
            return new CommonResult(444, "查询失败，查询id: " + id);
        }

    }


}
