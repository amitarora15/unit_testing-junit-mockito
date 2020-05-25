package com.amit.mocking;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
public class Order {

    private Long id;
    private @NonNull List<OrderItem> items;
    private Long discount;
    private Long totalPrice;

    @Builder @Getter
    public class OrderItem {

        private String productName;
        private Long price;
        private Long quantity;
    }

}
