package com.branco.grocerylist.cart.manager;

import com.branco.grocerylist.cart.model.ProductCounter;
import com.branco.grocerylist.common.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guilhermebranco on 5/3/17.
 */

public class CartManager {

    private Map<Integer, ProductCounter> products;

    public void addProduct(Product product) {
        if (products == null) {
            products = new HashMap<>();
        }
        ProductCounter counter;
        if (products.containsKey(product.getId())) {
            counter = products.get(product.getId());
        } else {
            counter = new ProductCounter(product);
            products.put(counter.getId(), counter);
        }
        counter.add();
    }

    public List<ProductCounter> getProducts() {
        if (products != null && !products.isEmpty()) {
            return new ArrayList<>(products.values());
        }
        return new ArrayList<>();
    }

    public void removeProduct(int id) {
        if (products == null || !products.containsKey(id)) {
            return;
        }
        ProductCounter counter = products.get(id);
        counter.remove();
        if (counter.getCount() == 0){
            products.remove(id);
        }
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        List<ProductCounter> products = getProducts();
        for (ProductCounter counter : products) {
            total = total.add(counter.getTotal());
        }
        return total;
    }

    public void reset(List<ProductCounter> products) {
        this.products = null;
        for (ProductCounter counter : products) {
            Product product = counter.convertToProduct();
            for (int i = 1; i <= counter.getCount(); i++){
                addProduct(product);
            }
        }
    }
}
