package com.branco.grocerylist.cart.ui.model;

import com.branco.grocerylist.cart.model.Cart;
import com.branco.grocerylist.cart.model.ProductCounter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartViewData {

  private Cart cart;
  private DecimalFormat formatter;
  private String currency;

  public CartViewData(Cart cart, DecimalFormat formatter, String currency) {
    this.cart = cart;
    this.formatter = formatter;
    this.currency = currency;
  }

  public Cart getCart() {
    return cart;
  }

  public String getTotal() {
    return formatter.format(cart.getTotal());
  }

  public String getCurrency() {
    return currency;
  }

  public List<SelectedProductViewData> getSelectedProducts() {
    List<SelectedProductViewData> products = new ArrayList<>();
    for (ProductCounter counter: cart.getProductCounterList()) {
      products.add(new SelectedProductViewData(counter, formatter));
    }
    return products;
  }
}
