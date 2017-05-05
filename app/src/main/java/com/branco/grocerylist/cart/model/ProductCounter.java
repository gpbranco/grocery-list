package com.branco.grocerylist.cart.model;

import com.branco.grocerylist.common.model.Product;

import java.math.BigDecimal;

/**
 * Created by guilhermebranco on 5/3/17.
 */

public class ProductCounter {

    private int id;
    private int count;
    private String name;
    private BigDecimal price;
    private BigDecimal total;

    public ProductCounter(Product product) {
        if (product == null) {
            throw new IllegalStateException("Product must not be null!");
        }
        this.id = product.getId();
        this.price = product.getPrice();
        this.name = product.getName();
        total = new BigDecimal(0);
    }

    public int getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public void add() {
        count++;
        total = total.add(price);
    }

    public void remove() {
        count--;
        total = total.subtract(price);
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getName() {
        return name;
    }

    public Product convertToProduct() {
        return new Product(id, name, price);
    }
}
