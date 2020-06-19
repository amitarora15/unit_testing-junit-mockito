package com.amit.mocking;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class OrderGenerator {

    private final OrderFactory factory;

    public String generateOrder(Map<String, Long> items, Long salePercentage){
        if(items != null && items.size() > 0){
            Long orderId = factory.getOrderId();
            System.out.println("Order Id " + orderId);
            List<Order.OrderItem> orderItems = factory.createOrder(items);
            Order order;
            if(salePercentage > 0)
                order = factory.getFinalOrder(orderItems, orderId, salePercentage);
            else
                order = factory.getFinalOrder(orderItems, orderId);
            System.out.println("Order is " + order);
            return order.getId() + ":" + order.getTotalPrice();
        } else {
            String errorMessage = factory.getErrorMessageForEmptyItems("Empty");
            return errorMessage;
        }
    }
}
