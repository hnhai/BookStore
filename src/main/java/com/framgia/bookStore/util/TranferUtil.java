package com.framgia.bookStore.util;

import com.framgia.bookStore.entity.OrderDetailEntity;
import com.framgia.bookStore.entity.OrderEntity;
import com.framgia.bookStore.form.OrderDTO;

public class TranferUtil {
    public static OrderDTO orderTranfer(OrderEntity entity){
        OrderDTO order = new OrderDTO();
        order.setId(entity.getId());
        order.setStatus(entity.getStatus());
        order.setUser(entity.getCustomer());
        order.setEmployee(entity.getEmployee());
        Long total = new Long(0);
        for (OrderDetailEntity od: entity.getOrderDetails()) {
            total = total + od.getQuantity() * od.getFinalPrice();
        }
        order.setTotalPrice(total);
        order.setPayment(entity.getPayments().iterator().next().getName());
        order.setOrderDate(entity.getBuyDay().toString());
        return order;
    }
}
