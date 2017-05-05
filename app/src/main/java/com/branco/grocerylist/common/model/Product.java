package com.branco.grocerylist.common.model;

import java.math.BigDecimal;

/**
 * Created by guilhermebranco on 5/3/17.
 */

public class Product {
    private int id;
    private String name;
    private BigDecimal price;

    public Product(int id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
