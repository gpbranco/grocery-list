package com.branco.grocerylist.product.ui;

import com.branco.grocerylist.product.ui.model.ProductViewData;

import java.util.List;

/**
 * Created by guilhermebranco on 5/4/17.
 */

public interface ProductsView {

    void showLoading();

    void hideLoading();

    void showProducts(List<ProductViewData> products);

    void showErrorMessage(String errorMessage);
}
