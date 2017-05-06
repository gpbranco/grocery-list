package com.branco.grocerylist.cart.ui.model;

import com.branco.grocerylist.cart.model.ProductCounter;

import java.text.DecimalFormat;

public class SelectedProductViewData {

  private ProductCounter counter;
  private DecimalFormat formatter;

  public SelectedProductViewData(ProductCounter couter, DecimalFormat formatter) {
    this.counter = couter;
    this.formatter = formatter;
  }

  public ProductCounter getProduct() {
    return counter;
  }

  public String getTotal() {
    return formatter.format(counter.getTotal());
  }

  public String getCount() {
    return String.valueOf(counter.getCount());
  }
}
