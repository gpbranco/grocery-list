package com.branco.grocerylist.cart.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by guilhermebranco on 5/4/17.
 */

public class Cart {

    private List<ProductCounter> productCounterList;
    private BigDecimal total;

    public Cart(List<ProductCounter> productCounterList, BigDecimal total) {
        this.productCounterList = productCounterList;
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<ProductCounter> getProductCounterList() {
        return productCounterList;
    }
}
