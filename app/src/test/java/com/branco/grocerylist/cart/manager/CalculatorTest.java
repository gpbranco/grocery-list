package com.branco.grocerylist.cart.manager;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by guilhermebranco on 5/3/17.
 */

public class CalculatorTest {

    @Test
    public void shouldExchangeBasedOnRate() {
        BigDecimal rate = new BigDecimal(1.08).setScale(2, BigDecimal.ROUND_UP);
        BigDecimal value = new BigDecimal(2.10).setScale(2, BigDecimal.ROUND_UP);
        BigDecimal expected = new BigDecimal(2.30).setScale(2, BigDecimal.ROUND_UP);

        BigDecimal total = new Calculator().exchange(value, rate).setScale(2, BigDecimal.ROUND_UP);

        assertEquals(expected, total);
    }
}
