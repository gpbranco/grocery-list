package com.branco.grocerylist.cart.ui;

import com.branco.grocerylist.cart.model.Cart;
import com.branco.grocerylist.cart.model.ProductCounter;

import java.util.List;

public interface CartView {

  void showProducts(List<ProductCounter> product);
  void showCartTotal(Cart cart);
}
