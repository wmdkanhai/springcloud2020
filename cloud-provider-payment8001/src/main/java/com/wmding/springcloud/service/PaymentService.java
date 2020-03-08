package com.wmding.springcloud.service;

import com.wmding.springcloud.entities.Payment;

/**
 * @author 明月
 * @version 1.0
 * @date 2020-03-08 17:45
 * @description:
 */
public interface PaymentService {

    int create(Payment payment);

    Payment getPayment(Long id);
}
