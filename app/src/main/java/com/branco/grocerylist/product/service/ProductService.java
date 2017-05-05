package com.branco.grocerylist.product.service;

import com.branco.grocerylist.common.model.Product;

import java.util.List;

import rx.Observable;

/**
 * Created by guilhermebranco on 5/4/17.
 */

public interface ProductService {

    Observable<List<Product>> requestProducts();
}
