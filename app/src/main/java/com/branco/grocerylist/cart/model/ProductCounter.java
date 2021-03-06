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
    private String unit;
    private BigDecimal price;
    private BigDecimal total;

    public ProductCounter(Product product) {
        if (product == null) {
            throw new IllegalStateException("Product must not be null!");
        }
        this.id = product.getId();
        this.price = product.getPrice();
        this.name = product.getName();
        this.unit = product.getUnit();
        this.total = new BigDecimal(0);
    }

    public ProductCounter convertedTotal(BigDecimal convertedTotal) {
        return new ProductCounter(this, convertedTotal);
    }

    private ProductCounter(ProductCounter productCounter, BigDecimal convertedTotal) {
        if (productCounter == null) {
            throw new IllegalStateException("ProductCounter must not be null!");
        }
        this.id = productCounter.getId();
        this.price = productCounter.getPrice();
        this.name = productCounter.getName();
        this.unit = productCounter.getUnit();
        this.count = productCounter.getCount();
        this.total = convertedTotal;
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

    public String getUnit() {
        return unit;
    }

    public Product convertToProduct() {
        return new Product(id, name, unit, price);
    }
}
