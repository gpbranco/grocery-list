package com.branco.grocerylist.common.model;

import java.math.BigDecimal;

/**
 * Created by guilhermebranco on 5/3/17.
 */

public class Product {
    private int id;
    private String name;
    private String unit;
    private BigDecimal price;

    public Product(int id, String name, String unit, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
