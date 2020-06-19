package com.amit.mocking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class OrderGeneratorSpyTest {
    @Spy
    private OrderFactory factory;

    @InjectMocks
    private OrderGenerator generatorUnderTest;

    private List<Order.OrderItem> items;

    private Order order;

    private Order disCountedOrder;

    private Map<String, Long> orderItems;


    @BeforeEach
    public void initializeOrder(){
        items = new ArrayList<>();
        items.add(Order.OrderItem.builder().price(20L).productName("Test").quantity(2L).build());
        order = new Order(2000L, items, 0L, 200L);
        disCountedOrder = new Order(2001L, items, 20L, 100L);
        orderItems = new HashMap<>();
        orderItems.put("Keychain", 20L);
    }

    @Test
    void givenItemsAndNoSaleWhenOrderGenerationWithSpyThenGetOrderDetails(){
        doAnswer((a) -> items).when(factory).createOrder(anyMap());
        doReturn(order).when(factory).getFinalOrder(isA(ArrayList.class), anyLong());

        String orderString = generatorUnderTest.generateOrder(orderItems,0L);
        assertEquals("2000:200",orderString);

        verify(factory, never()).getFinalOrder(anyList(), anyLong(), anyLong());
        verify(factory).getOrderId();
        verify(factory).createOrder(any());
        verify(factory).getFinalOrder(any(), any());
        verifyNoMoreInteractions(factory);
    }

}

