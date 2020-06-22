package com.amit.mocking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.AdditionalAnswers.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class OrderGeneratorTest {

    @Mock private OrderFactory factory;

    @InjectMocks private OrderGenerator generatorUnderTest;

    @Captor
    private ArgumentCaptor<String> captor;

    @Captor
    private ArgumentCaptor<Long> longCaptor;

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
    void givenNullItemsWhenOrderGenerationThenDoNothing(){
        when(factory.getErrorMessageForEmptyItems(anyString())).thenAnswer(returnsFirstArg());
        //given(factory.getErrorMessageForEmptyItems(any())).willReturn(returnsFirstArg());
        String message = generatorUnderTest.generateOrder(null, 0L);
        assertEquals("Empty", message, "Message should be empty");
        //verify(factory, times(1)).getOrderId();

        verify(factory, atLeast(1)).getErrorMessageForEmptyItems(captor.capture());
        //then(factory).should().getErrorMessageForEmptyItems(captor.capture());
        String argument = captor.getValue();
        assertEquals("Empty", argument);

        verifyNoMoreInteractions(factory);
    }

    @Test
    void givenEmptyItemsWhenOrderGenerationThenDoNothing(){
        when(factory.getErrorMessageForEmptyItems(any())).thenReturn("Test");

        String message = generatorUnderTest.generateOrder(new HashMap<>(), 0L);
        assertEquals("Test", message);

        verify(factory, never()).getOrderId();
        verify(factory, times(1)).getErrorMessageForEmptyItems(ArgumentMatchers.eq("Empty"));
        verifyNoMoreInteractions(factory);
    }

    @Test
    void givenEmptyItemsWhenOrderGenerationThenThrowException(){
        when(factory.getErrorMessageForEmptyItems(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> generatorUnderTest.generateOrder(new HashMap<>(), 0L));

        verify(factory, never()).getOrderId();
        verify(factory, times(1)).getErrorMessageForEmptyItems(ArgumentMatchers.eq("Empty"));
        verifyNoMoreInteractions(factory);
    }

    @Test
    void givenItemsAndNoSaleWhenOrderGenerationThenGetOrderDetails(){
       when(factory.getOrderId()).thenReturn(1L);
       when(factory.createOrder(anyMap())).thenAnswer((map) -> items);
       when(factory.getFinalOrder(isA(ArrayList.class), anyLong())).thenReturn(order);

       String orderString = generatorUnderTest.generateOrder(orderItems,0L);
       assertEquals("2000:200",orderString);

       verify(factory, never()).getFinalOrder(anyList(), anyLong(), anyLong());
       verify(factory).getOrderId();
       verify(factory).createOrder(any());
       verify(factory).getFinalOrder(any(), ArgumentMatchers.eq(1L));
       verifyNoMoreInteractions(factory);
    }

    @Test
    public void givenItemsAndSaleWhenOrderGenerationThenGetOrderDetails(){
        when(factory.getOrderId()).thenReturn(1L);
        when(factory.createOrder(isA(HashMap.class))).thenReturn(items);
        when(factory.getFinalOrder(any(),anyLong(), anyLong())).thenReturn(disCountedOrder);
        //when(factory.getFinalOrder(isA(ArrayList.class), anyLong())).thenReturn(order);

        String orderString = generatorUnderTest.generateOrder(orderItems,20L);
        assertEquals("2001:100",orderString);

        //verify(factory, never()).getFinalOrder(anyList(), anyLong(), anyLong());
        verify(factory, never()).getFinalOrder(anyList(), anyLong());
        verify(factory).getOrderId();
        verify(factory).createOrder(any());

        verify(factory).getFinalOrder(any(), anyLong(), longCaptor.capture());
        Long discount = longCaptor.getValue();
        assertEquals(20L, discount);
        verifyNoMoreInteractions(factory);
    }


}
