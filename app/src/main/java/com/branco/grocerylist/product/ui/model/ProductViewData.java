package com.branco.grocerylist.product.ui.model;

import com.branco.grocerylist.common.model.Product;

import java.text.NumberFormat;

public class ProductViewData {

  private Product product;
  private NumberFormat formatter;

  public ProductViewData(Product product, NumberFormat formatter) {
    this.product = product;
    this.formatter = formatter;
  }

  public Product getProduct() {
    return product;
  }

  public String getPrice() {
    return formatter.format(product.getPrice());
  }
}
