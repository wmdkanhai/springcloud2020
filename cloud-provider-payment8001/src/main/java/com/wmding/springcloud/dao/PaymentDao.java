package com.wmding.springcloud.dao;

import com.wmding.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 明月
 * @version 1.0
 * @date 2020-03-08 17:38
 * @description:
 */

@Mapper
public interface PaymentDao {

    int create(Payment payment);

    Payment getPaymentById(@Param("id") long id);
}
