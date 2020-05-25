package com.amit.mocking;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class OrderGenerator {

    private final OrderFactory factory;

    private final Long salePercentage;

    public void generateOrder(Map<String, Long> items){
        if(items != null && items.size() > 0){
            Long orderId = factory.getOrderId();
            List<Order.OrderItem> orderItems = factory.createOrder(items);
            Order order;
            if(salePercentage > 0)
                order = factory.getFinalOrder(orderItems, orderId, salePercentage);
            else
                order = factory.getFinalOrder(orderItems, orderId);
            System.out.println("Order is " + order);
        }
    }
}
