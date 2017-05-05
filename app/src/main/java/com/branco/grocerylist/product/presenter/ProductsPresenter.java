package com.branco.grocerylist.product.presenter;

import android.util.Log;

import com.branco.grocerylist.common.model.Product;
import com.branco.grocerylist.product.interactor.ProductsInteractor;
import com.branco.grocerylist.product.ui.ProductsView;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by guilhermebranco on 5/4/17.
 */

public class ProductsPresenter {

    private static final String TAG = ProductsPresenter.class.getSimpleName();

    private ProductsView producsView;
    private ProductsInteractor productsInteractor;
    private Subscription subscription;

    public ProductsPresenter(ProductsView producsView, ProductsInteractor productsInteractor) {
        this.producsView = producsView;
        this.productsInteractor = productsInteractor;
    }

    public void loadProducts() {
        subscription = productsInteractor
                .loadProducts()
                .subscribe(new Subscriber<List<Product>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "loadProducts.onCompleted");
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "loadProducts.onError", error);
                    }

                    @Override
                    public void onNext(List<Product> products) {
                        Log.d(TAG, "loadProducts.onNext");
                        producsView.hideLoading();
                        producsView.showProducts(products);
                    }
                });
    }
}
