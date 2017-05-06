package com.branco.grocerylist.product.service;

import com.branco.grocerylist.common.model.Product;

import rx.Observable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceInMemory implements ProductService {

  @Override
  public Observable<List<Product>> requestProducts() {
    return Observable.just(products());
  }

  private List<Product> products() {
    List<Product> products = new ArrayList<>();
    products.add(new Product(1, "Peas", "bag", new BigDecimal(0.95)));
    products.add(new Product(2, "Eggs", "dozen", new BigDecimal(2.10)));
    products.add(new Product(3, "Beans", "can", new BigDecimal(0.73)));
    products.add(new Product(4, "Milk", "bottle", new BigDecimal(1.40)));
    return products;
  }
}
