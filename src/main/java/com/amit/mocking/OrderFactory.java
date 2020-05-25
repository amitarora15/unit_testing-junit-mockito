package com.amit.mocking;

import java.util.*;
import java.util.stream.Collectors;

public class OrderFactory {

    private static Map<String, Long> inventory = new HashMap<>();

    private static Map<String, Long> price = new HashMap<>();

    static {
        inventory.put("Keychain", 10L);
        inventory.put("Pot", 100L);
        inventory.put("Shirt", 200L);

        price.put("Keychain", 10L);
        price.put("Pot", 100L);
        price.put("Shirt", 200L);
    }

    public List<Order.OrderItem> createOrder(Map<String, Long> items) {

        return items.entrySet().stream().map(e ->
                {
                    String product = e.getKey();
                    Long quantity = e.getValue();

                    Long availableQuantity = inventory.get(product);
                    Long quotedPrice = price.get(product);
                    if (availableQuantity != null && quotedPrice != null && availableQuantity > quantity) {
                        return Order.OrderItem.builder().productName(product).quantity(quantity).price(quotedPrice * quantity).build();
                    }
                    return null;
                }
        ).collect(Collectors.toList());
    }

    public Long getOrderId(){
         Random random = new Random();
         return random.nextLong();
    }

    public Order getFinalOrder(List<Order.OrderItem> items, Long orderId, Long discount){
        Long total = getPrice(items);
        total -= ((discount * total)/100);
        Order order = new Order(orderId, items, discount, total);
        return order;
    }

    public Order getFinalOrder(List<Order.OrderItem> items, Long orderId){
        Long total = getPrice(items);
        Order order = new Order(orderId, items, 0L, total);
        return order;
    }

    private Long getPrice(List<Order.OrderItem> items){
        return items.stream().filter(Objects::nonNull).mapToLong(a -> a.getPrice()).sum();
    }
}
