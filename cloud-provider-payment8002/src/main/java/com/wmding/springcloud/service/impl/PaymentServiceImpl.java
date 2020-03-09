package com.wmding.springcloud.service.impl;

import com.wmding.springcloud.dao.PaymentDao;
import com.wmding.springcloud.entities.Payment;
import com.wmding.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 明月
 * @version 1.0
 * @date 2020-03-08 17:46
 * @description:
 */
@Service
public class PaymentServiceImpl implements PaymentService
{

    @Autowired
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPayment(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
