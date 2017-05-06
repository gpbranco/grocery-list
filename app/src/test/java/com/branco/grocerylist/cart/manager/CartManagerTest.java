package com.branco.grocerylist.cart.manager;

import com.branco.grocerylist.cart.model.ProductCounter;
import com.branco.grocerylist.common.model.Product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import rx.observers.TestSubscriber;

import static org.junit.Assert.*;

/**
 * Created by guilhermebranco on 5/3/17.
 */

public class CartManagerTest {

    private CartManager cartManager;
    private TestSubscriber<Map<Integer, ProductCounter>> testSubscriber;

    @Before
    public void setUp() {
        cartManager = new CartManager();
    }

    @Test
    public void shouldAddProduct() {
        Product one = new Product(1, "", "", BigDecimal.ONE);
        Product two = new Product(2, "", "", BigDecimal.ONE);
        cartManager.addProduct(one);
        cartManager.addProduct(two);
        List<ProductCounter> products = cartManager.getProducts();
        assertEquals(2, products.size());
    }

    @Test
    public void shouldRemoveProduct() {
        int id = 1;
        Product product = new Product(id, "", "", BigDecimal.ONE);
        cartManager.addProduct(product);
        cartManager.addProduct(product);
        cartManager.addProduct(product);
        cartManager.removeProduct(id);
        List<ProductCounter> products = cartManager.getProducts();
        assertEquals(2, products.get(0).getCount());
    }

    @Test
    public void shouldCountProduct() {
        Product product = new Product(1, "", "", BigDecimal.ONE);
        cartManager.addProduct(product);
        cartManager.addProduct(product);
        ProductCounter counter = cartManager.getProducts().get(0);
        assertEquals(2, counter.getCount());
    }

    @Test
    public void shouldCalculateTotalForEachProductCounter() {
        Product product = new Product(1, "", "", BigDecimal.ONE);
        ProductCounter counter = new ProductCounter(product);
        counter.add();
        counter.add();
        counter.add();
        counter.add();
        counter.remove();
        assertEquals(new BigDecimal(3), counter.getTotal());
    }

    @Test
    public void shouldCalculateTotalForAllProducts() {
        Product one = new Product(1, "", "", new BigDecimal(10));
        Product two = new Product(2, "", "", new BigDecimal(5));
        cartManager.addProduct(one);
        cartManager.addProduct(one);
        cartManager.addProduct(one);
        cartManager.addProduct(two);
        cartManager.addProduct(two);
        assertEquals(new BigDecimal(40), cartManager.getTotal());

    }
}
