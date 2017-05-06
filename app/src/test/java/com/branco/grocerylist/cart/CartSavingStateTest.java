package com.branco.grocerylist.cart;

import com.branco.grocerylist.cart.model.Cart;
import com.branco.grocerylist.cart.model.ProductCounter;
import com.branco.grocerylist.common.model.Product;
import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartSavingStateTest {

  @Test
  public void shouldConvertCart() {
    Gson gson = new Gson();
    List<ProductCounter> products = new ArrayList<>();
    products.add(new ProductCounter(new Product(1, "one", "", BigDecimal.ONE)));
    products.add(new ProductCounter(new Product(2, "two", "", new BigDecimal(2.52))));
    Cart cart = new Cart(products, new BigDecimal(100.00));
    String json = gson.toJson(cart);
    Cart converted = gson.fromJson(json, Cart.class);
    Assert.assertEquals(cart.getProductCounterList().size(), converted.getProductCounterList().size());
    Assert.assertEquals(cart.getTotal().toString(), converted.getTotal().toString());
  }
}
