package com.branco.grocerylist.cart.ui.model;

import com.branco.grocerylist.cart.model.Cart;
import com.branco.grocerylist.cart.model.ProductCounter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartViewData {

  private Cart cart;
  private DecimalFormat formatter;

  public CartViewData(Cart cart, DecimalFormat formatter) {
    this.cart = cart;
    this.formatter = formatter;
  }

  public Cart getCart() {
    return cart;
  }

  public String getTotal() {
    return formatter.format(cart.getTotal());
  }

  public List<SelectedProductViewData> getSelectedProduct() {
    List<SelectedProductViewData> products = new ArrayList<>();
    for (ProductCounter counter: cart.getProductCounterList()) {
      products.add(new SelectedProductViewData(counter, formatter));
    }
    return products;
  }
}
