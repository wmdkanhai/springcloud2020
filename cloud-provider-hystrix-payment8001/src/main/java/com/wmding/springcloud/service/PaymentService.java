package com.wmding.springcloud.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 明月
 * @version 1.0
 * @date 2020-03-16 09:22
 * @description:
 */
@Service
public class PaymentService {

    /**
     * 正常返回
     *
     * @param id
     * @return
     */
    public String paymentInfo_ok(Integer id) {
        String msg = "线程池" + Thread.currentThread().getName() + "paymentInfo_ok,id:" + id;
        return msg;
    }


    /**
     * 超时返回
     *
     * @param id
     * @return
     */
    public String paymentInfo_TimeOut(Integer id) {
        int timeNumber = 3;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String msg = "线程池" + Thread.currentThread().getName() + "paymentInfo_TimeOut,id:" + id + "\t耗时: " + timeNumber;
        return msg;
    }
}
