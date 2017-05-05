package com.branco.grocerylist.cart.manager;

import com.branco.grocerylist.cart.model.ProductCounter;

import java.math.BigDecimal;

/**
 * Created by guilhermebranco on 5/3/17.
 */

public class Calculator {

    public BigDecimal getTotal(ProductCounter counter, BigDecimal rate) {
        return counter.getPrice().multiply(rate).multiply(new BigDecimal(counter.getCount()));
    }

    public BigDecimal exchange(BigDecimal value, BigDecimal rate) {
        return value.multiply(rate);
    }
}
