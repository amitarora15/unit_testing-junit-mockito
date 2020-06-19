package com.amit.mocking;

import lombok.*;

import java.util.List;

@Value
public class Order {

    private @NonNull Long id;
    private @NonNull List<OrderItem> items;
    private @NonNull Long discount;
    private @NonNull Long totalPrice;

    @Builder @Getter
    @ToString
    static class OrderItem {

        private String productName;
        private Long price;
        private Long quantity;
    }

}
