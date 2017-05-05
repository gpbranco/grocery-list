package com.branco.grocerylist.product.ui;

import com.branco.grocerylist.common.model.Product;

import java.util.List;

/**
 * Created by guilhermebranco on 5/4/17.
 */

public interface ProductsView {
    void hideLoading();

    void showProducts(List<Product> products);
}
