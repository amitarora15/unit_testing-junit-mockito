package com.amit.mocking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class OrderGeneratorTest {

    @Mock private OrderFactory factory;

    @InjectMocks private OrderGenerator generator;

    @Test
    void givenEmptyItemsWhenOrderGenerationThenDoNothing(){

    }
}
