package com.branco.grocerylist.cart.ui.model;

import com.branco.grocerylist.cart.model.ProductCounter;

import java.text.DecimalFormat;

public class SelectedProductViewData {

  private ProductCounter couter;
  private DecimalFormat formatter;

  public SelectedProductViewData(ProductCounter couter, DecimalFormat formatter) {
    this.couter = couter;
    this.formatter = formatter;
  }

  public ProductCounter getProduct() {
    return couter;
  }

  public String getTotal() {
    return formatter.format(couter.getTotal());
  }
}
